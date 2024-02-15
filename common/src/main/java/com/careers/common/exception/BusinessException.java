package com.careers.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    protected String code;
    protected String message;
    protected int httpStatus;

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.name();
        this.message = exceptionCode.getMessage();
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public BusinessException(String code, String message, int httpStatus) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
