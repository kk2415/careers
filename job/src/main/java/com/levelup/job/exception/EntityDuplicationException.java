package com.levelup.job.exception;

public class EntityDuplicationException extends BusinessException {

    public EntityDuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
