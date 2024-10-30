package com.gmdrive.gmdrive.global.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    // 인증 - 토큰
    NOT_EXISTS_AUTH_HEADER(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "Authorization Header가 빈 값입니다.")),
    NOT_VALID_BEARER_GRANT_TYPE(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "인증 타입이 Bearer 타입이 아닙니다.")),
    TOKEN_EXPIRED(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "해당 token은 만료되었습니다.")),
    ACCESS_TOKEN_EXPIRED(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "해당 access token은 만료되었습니다.")),
    NOT_ACCESS_TOKEN_TYPE(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "tokenType이 access token이 아닙니다.")),
    REFRESH_TOKEN_EXPIRED(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "해당 refresh token은 만료되었습니다.")),
    REFRESH_TOKEN_NOT_FOUND(ErrorStatus.of(HttpStatus.BAD_REQUEST, "해당 refresh token은 존재하지 않습니다.")),
    NOT_VALID_TOKEN(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.")),
    NO_ACCESS_USER(ErrorStatus.of(HttpStatus.FORBIDDEN, "권한이 없습니다.")),

    // 인증 - Spring Security
    ANONYMOUS_USER(ErrorStatus.of(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.")),
    ;
    private final ErrorStatus errorStatus;

    AuthErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
