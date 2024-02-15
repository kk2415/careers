package com.careers.job.security.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.careers.common.exception.ExceptionCode;
import com.careers.job.presentation.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 인증객체(Authentication) 없이 보호자원에 접근 시도하면 동작하는 핸들러
 * */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationNoAuthenticationHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ExceptionCode exceptionCode = (ExceptionCode) request.getAttribute(JwtAuthorizationFilter.JWT_EXCEPTION_KEY);

        ExceptionResponse responseBody = ExceptionResponse.of(
                exceptionCode.name(),
                exceptionCode.getMessage(),
                exceptionCode.getHttpStatus()
        );

        response.setStatus(responseBody.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
