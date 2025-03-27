package com.file_jy.global.error.errorcode;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    LOGIN_ID_ALREADY_EXISTS(ErrorStatus.of(HttpStatus.CONFLICT, "이미 가입된 ID입니다")),
    INVALID_USERNAME(ErrorStatus.of(HttpStatus.BAD_REQUEST, "사용자명은 2~20자 사이여야 합니다")),
    INVALID_LOGIN_ID(ErrorStatus.of(HttpStatus.BAD_REQUEST, "ID는 2~10자 사이여야 합니다")),
    INVALID_PASSWORD(ErrorStatus.of(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다")),
    ;

    private final ErrorStatus errorStatus;

    UserErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
