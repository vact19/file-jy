package com.gmdrive.gmdrive.global.error.errorcode;

import org.springframework.http.HttpStatus;

public enum HttpErrorCode implements ErrorCode {
    // Jackson 파싱
    HTTP_MESSAGE_NOT_READABLE(ErrorStatus.of(HttpStatus.BAD_REQUEST, "요청값을 읽어들이지 못했습니다. 형식을 확인해 주세요.")),
    ;
    private final ErrorStatus errorStatus;

    HttpErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
