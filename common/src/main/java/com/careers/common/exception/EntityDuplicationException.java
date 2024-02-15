package com.careers.common.exception;

public class EntityDuplicationException extends BusinessException {

    public EntityDuplicationException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
