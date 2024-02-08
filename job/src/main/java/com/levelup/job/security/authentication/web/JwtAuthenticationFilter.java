package com.levelup.job.security.authentication.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.common.exception.ExceptionCode;
import com.levelup.job.presentation.exception.ExceptionResponse;
import com.levelup.job.presentation.controller.v1.dto.LoginDto;
import com.levelup.job.security.authentication.CustomAuthenticationToken;
import com.levelup.job.security.userdetails.User;
import com.levelup.job.util.jwt.AccessToken;
import com.levelup.job.util.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        LoginDto.LoginRequest loginRequest = createLoginRequest(request);

        //ProviderManager
        AuthenticationManager authenticationManager = getAuthenticationManager();

        return authenticationManager.authenticate(
                new CustomAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password(),
                        new ArrayList<>(10)
                )
        );
    }

    private LoginDto.LoginRequest createLoginRequest(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), LoginDto.LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(createResponseBody(user));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
    }

    private String createResponseBody(User user) throws JsonProcessingException {
        String token = tokenProvider.createAccessToken(user.getRole());
        AccessToken accessToken = AccessToken.of(
                token,
                tokenProvider.getExpiration(token),
                tokenProvider.getIssuedAt(token)
        );

        return objectMapper.writeValueAsString(LoginDto.LoginResponse.of(accessToken, user.getRole()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ExceptionCode exceptionCode = ExceptionCode.INVALID_PASSWORD;
        if (exception instanceof InternalAuthenticationServiceException) {
            exceptionCode = ExceptionCode.USERNAME_NOT_FOUND;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(ExceptionResponse.of(
                exceptionCode.name(),
                exceptionCode.getMessage(),
                exceptionCode.getHttpStatus()
        )));

        SecurityContextHolder.clearContext();
    }
}
