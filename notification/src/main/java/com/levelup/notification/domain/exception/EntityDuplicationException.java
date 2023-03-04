package com.levelup.notification.domain.exception;

public class EntityDuplicationException extends BusinessException {

    public EntityDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
