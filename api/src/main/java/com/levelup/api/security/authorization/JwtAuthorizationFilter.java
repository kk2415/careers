package com.levelup.api.security.authorization;

import com.levelup.api.security.userdetails.User;
import com.levelup.api.util.jwt.AuthenticationErrorCode;
import com.levelup.api.util.jwt.TokenProvider;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        parseBearerHeader(request)
                .ifPresentOrElse((token) -> {
                            AuthenticationErrorCode validationResult = tokenProvider.validateToken(token);
                            SecurityContext securityContext = SecurityContextHolder.getContext();

                            if (validationResult.isValid()) {
                                String role = tokenProvider.getSubject(token);

                                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                        User.createMock(),
                                        null,
                                        Collections.singleton(new SimpleGrantedAuthority(role)));
                                securityContext.setAuthentication(authenticationToken);
                            } else {
                                request.setAttribute("AuthenticationErrorCode", validationResult);
                            }
                        },
                        () -> {
                            request.setAttribute("AuthenticationErrorCode", AuthenticationErrorCode.NULL_BEARER_HEADER);
                        });

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
