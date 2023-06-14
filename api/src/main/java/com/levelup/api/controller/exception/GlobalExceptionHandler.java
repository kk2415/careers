package com.levelup.api.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity.status(500).body(ExceptionResponse.of(
                "EXCEPTION",
                e.getMessage(),
                500
        ));
    }
}
