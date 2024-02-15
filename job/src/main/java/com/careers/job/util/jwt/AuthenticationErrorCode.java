package com.careers.job.util.jwt;

import lombok.Getter;

@Getter
public enum AuthenticationErrorCode {

    SUCCESS(200, "정상적인 토큰입니다.", true),
    FAIL_AUTHENTICATION(401, "사용자 인증 실패", false),

    NULL_BEARER_HEADER(400, "로그인이 필요한 서비스입니다.", false),
    NULL_TOKEN(400, "로그인이 필요한 서비스입니다.", false),
    INVALID_TOKEN(400, "유효하지 않은 인증 토큰입니다.", false),
    EXPIRED_TOKEN(400, "로그인이 만료되었습니다. 다시 로그인해주세요", false),
    INVALID_SIGNATURE(400, "유효하지 않은 토큰 시그니처입니다.", false);

    private final int httpStatus;
    private final boolean valid;
    private final String message;

    AuthenticationErrorCode(int httpStatus, String message, boolean validation) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.valid = validation;
    }
}
