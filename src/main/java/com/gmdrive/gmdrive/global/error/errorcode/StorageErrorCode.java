package com.gmdrive.gmdrive.global.error.errorcode;

import org.springframework.http.HttpStatus;

public enum StorageErrorCode implements ErrorCode {
    NO_STORAGE_NAME(ErrorStatus.of(HttpStatus.BAD_REQUEST, "저장소 이름은 필수로 설정해야 합니다")),
    NO_STORAGE_OWNER(ErrorStatus.of(HttpStatus.BAD_REQUEST, "저장소 생성자가 존재해야 합니다")),
    NO_STORAGE_TYPE(ErrorStatus.of(HttpStatus.BAD_REQUEST, "저장소 타입이 존재해야 합니다")),
    ;

    private final ErrorStatus errorStatus;

    StorageErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
