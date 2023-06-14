package com.levelup.api.security.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.api.controller.exception.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        ExceptionResponse responseBody = ExceptionResponse.of("ROLE_NOT_FOUND", "권한이 없습니다.", 400);

        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), "ROLE_NOT_FOUND" + " " + "권한이 없습니다.");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
