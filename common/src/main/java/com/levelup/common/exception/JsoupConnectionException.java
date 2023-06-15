package com.levelup.common.exception;

public class JsoupConnectionException extends JobException {

    public JsoupConnectionException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public JsoupConnectionException(String url, ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage() + " - " + url, exceptionCode.getHttpStatus());
    }
}
