package com.file_jy.global.error.errorcode;

import org.springframework.http.HttpStatus;

public enum StorageFileErrorCode implements ErrorCode {
    NOT_ENOUGH_AUTHORITY(ErrorStatus.of(HttpStatus.FORBIDDEN, "파일 접근 권한이 부족합니다")),
    NO_UPLOADER(ErrorStatus.of(HttpStatus.BAD_REQUEST, "업로더 정보가 존재하지 않습니다")),
    EMPTY_FILE(ErrorStatus.of(HttpStatus.BAD_REQUEST, "파일 크기가 존재하지 않습니다")),
    NO_STORAGE(ErrorStatus.of(HttpStatus.BAD_REQUEST, "저장소 정보가 존재하지 않습니다"))
    ;

    private final ErrorStatus errorStatus;

    StorageFileErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
