package com.levelup.job.exception;

public class JsoupConnectionException extends JobException {

    public JsoupConnectionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JsoupConnectionException(String url, ErrorCode errorCode) {
        super(errorCode.getMessage() + " - " + url, errorCode.getHttpStatus());
    }
}
