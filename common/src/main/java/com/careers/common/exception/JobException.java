package com.careers.common.exception;

public class JobException extends BusinessException {

    public JobException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public JobException(String message, int httpStatus) {
        super("JOB_EXCEPTION", message, httpStatus);
    }
}
