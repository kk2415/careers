package com.levelup.job.exception;

public class FileNotFoundException extends BusinessException {

    public FileNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
