package com.levelup.job.presentation.exception;

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
@RestControllerAdvice("JobGlobalExceptionHandler")
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Void> handleThrowable(Throwable e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(500).body(ExceptionResponse.of(ExceptionCode.EXCEPTION.name(), e.getMessage(), ExceptionCode.EXCEPTION.getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(ExceptionCode.INVALID_REQUEST_BODY.getHttpStatus()).body(FieldExceptionResponse.of(ExceptionCode.INVALID_REQUEST_BODY, e.getBindingResult()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(ExceptionCode.FILE_SIZE_LIMIT_EXCEEDED.getHttpStatus()).body(ExceptionResponse.of(
                ExceptionCode.FILE_SIZE_LIMIT_EXCEEDED.name(),
                ExceptionCode.FILE_SIZE_LIMIT_EXCEEDED.getMessage(),
                ExceptionCode.FILE_SIZE_LIMIT_EXCEEDED.getHttpStatus())
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(e.getHttpStatus()).body(ExceptionResponse.of(e.getCode(), e.getMessage(), e.getHttpStatus()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(e.getHttpStatus()).body(ExceptionResponse.of(e.getCode(), e.getMessage(), e.getHttpStatus()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionResponse.of(ExceptionCode.SELENIUM_EXCEPTION.name(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(WebDriverException.class)
    public ResponseEntity<ExceptionResponse> handleWebDriverException(WebDriverException e, HttpServletRequest request) {
        log.error("{} - request uri: {}", request.getMethod(), request.getRequestURI(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionResponse.of(ExceptionCode.SELENIUM_EXCEPTION.name(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
