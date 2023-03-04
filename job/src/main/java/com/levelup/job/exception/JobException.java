package com.levelup.job.exception;

public class JobException extends BusinessException {

    public JobException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JobException(String message, int httpStatus) {
        super(message, httpStatus);
    }
}
