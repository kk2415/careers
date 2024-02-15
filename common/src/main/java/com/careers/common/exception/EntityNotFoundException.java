package com.careers.common.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
