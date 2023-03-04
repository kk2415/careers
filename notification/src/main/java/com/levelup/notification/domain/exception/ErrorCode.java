package com.levelup.notification.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    FCM_TOPIC_NOT_FOUND(400, "존재하지 않는 FCM 토픽입니다."),
    FCM_DEVICE_TOKEN_NOT_FOUND(400, "존재하지 않는 FCM 디바이스 토큰입니다."),
    ;

    private final int httpStatus;
    private final String message;

    ErrorCode(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
