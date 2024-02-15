package com.careers.job.security.authorization;

import com.careers.common.exception.ExceptionCode;
import com.careers.job.security.userdetails.User;
import com.careers.job.util.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    public static final String JWT_EXCEPTION_KEY = "INVALID_JWT_KEY";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        parseBearerHeader(request)
                .ifPresentOrElse(token -> {
                    try {
                        tokenProvider.validate(token);

                        String role = tokenProvider.getSubject(token);
                        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                User.createMock(),
                                null,
                                Collections.singleton(new SimpleGrantedAuthority(role)));

                        SecurityContext securityContext = SecurityContextHolder.getContext();
                        securityContext.setAuthentication(authenticationToken);
                    } catch (ExpiredJwtException e) {
                        request.setAttribute(JWT_EXCEPTION_KEY, ExceptionCode.EXPIRED_TOKEN);
                    } catch (IllegalArgumentException e) {
                        request.setAttribute(JWT_EXCEPTION_KEY, ExceptionCode.NULL_TOKEN);
                    } catch (MalformedJwtException e) {
                        request.setAttribute(JWT_EXCEPTION_KEY, ExceptionCode.INVALID_TOKEN);
                    }
                    },
                        () -> request.setAttribute(JWT_EXCEPTION_KEY, ExceptionCode.NULL_BEARER_HEADER)
                );

        filterChain.doFilter(request, response);
    }

    private Optional<String> parseBearerHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = null;

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return Optional.ofNullable(token);
    }
}
