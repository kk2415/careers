package com.levelup.crawler.exception;

import com.levelup.common.exception.BusinessException;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity.status(ExceptionCode.INVALID_REQUEST_BODY.getHttpStatus()).build();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity.status(ExceptionCode.FILE_SIZE_LIMIT_EXCEEDED.getHttpStatus()).build();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Void> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        e.printStackTrace();

        return ResponseEntity.status(e.getHttpStatus()).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(WebDriverException.class)
    public ResponseEntity<Void> handleWebDriverException(WebDriverException e, HttpServletRequest request) {
        log.error("{} - request uri: {}, message: {}", request.getMethod(), request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
