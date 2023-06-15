package com.levelup.common.exception;

public class EntityDuplicationException extends BusinessException {

    public EntityDuplicationException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
