package com.file_jy.global.error.errorcode;

import org.springframework.http.HttpStatus;

public enum CommonErrorCode implements ErrorCode {
    UNCOVERED_ENUM_CASE(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "처리되지 않은 ENUM 케이스가 존재합니다."))
    ;

    private final ErrorStatus errorStatus;

    CommonErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
