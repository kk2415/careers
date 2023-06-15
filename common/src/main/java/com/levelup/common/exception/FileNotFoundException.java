package com.levelup.common.exception;

public class FileNotFoundException extends BusinessException {

    public FileNotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
