package com.levelup.api.security.authentication.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.api.controller.exception.ExceptionResponse;
import com.levelup.api.controller.v1.dto.LoginDto;
import com.levelup.api.security.LoginException;
import com.levelup.api.security.authentication.CustomAuthenticationToken;
import com.levelup.api.security.userdetails.LoginType;
import com.levelup.api.security.userdetails.Role;
import com.levelup.api.security.userdetails.User;
import com.levelup.api.util.jwt.AccessToken;
import com.levelup.api.util.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto.LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginDto.LoginRequest.class);

            //ProviderManager
            AuthenticationManager authenticationManager = getAuthenticationManager();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password(),
                            new ArrayList<>(10)
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        String token = tokenProvider.createAccessToken(user.getRoles().get(0).toString());
        AccessToken accessToken = AccessToken.of(
                token,
                tokenProvider.getExpiration(token),
                tokenProvider.getIssuedAt(token));

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(getResponseBody((CustomAuthenticationToken) authResult, user, accessToken));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
    }

    private String getResponseBody(CustomAuthenticationToken authenticationToken, User user, AccessToken accessToken) throws JsonProcessingException {
        String role = user.getRoles().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(Role.ANONYMOUS.toString());

        if (LoginType.USER.equals(authenticationToken.getLoginType())) {
            return objectMapper.writeValueAsString(LoginDto.LoginResponse.of(
                    accessToken,
                    role));
        }

        return objectMapper.writeValueAsString(LoginDto.LoginResponse.of(accessToken, role));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String exceptionMsg = "비밀번호가 일치하지 않습니다.";
        if (exception instanceof InternalAuthenticationServiceException) {
            exceptionMsg = "아이디가 일치하지 않습니다.";
        } else if (exception instanceof LoginException) {
            exceptionMsg = "잘못된 로그인 URI 입니다.";
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(ExceptionResponse.of(
                exceptionMsg,
                exceptionMsg,
                HttpStatus.UNAUTHORIZED.value())));

        SecurityContextHolder.clearContext();
    }
}
