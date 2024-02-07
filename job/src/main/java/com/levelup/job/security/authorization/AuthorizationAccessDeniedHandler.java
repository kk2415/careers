package com.levelup.job.security.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.common.exception.ExceptionCode;
import com.levelup.job.controller.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 인가(Authorization) 작업 중 권한(Role)이 없으면 동작하는 핸들러
 * */
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ExceptionResponse responseBody = ExceptionResponse.of(
                ExceptionCode.INVALID_PERMISSION.name(),
                ExceptionCode.INVALID_PERMISSION.getMessage(),
                ExceptionCode.INVALID_PERMISSION.getHttpStatus()
        );

        response.setStatus(responseBody.getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
