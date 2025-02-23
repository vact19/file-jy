package com.file_jy.global.error.errorcode;

import org.springframework.http.HttpStatus;

public enum FileErrorCode implements ErrorCode {
    // 파일
    FILE_CANNOT_BE_STORED(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 저장할 수 없습니다.")),
    FILE_CANNOT_BE_READ(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 읽을 수 없습니다.")),
    FAILED_RESPONSE(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "읽어들인 파일을 전송할 수 없습니다")),
    UNSAVED_FILE_EXISTS(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "저장되지 않은 파일이 존재합니다.")),
    MULTIPART_FILE_CANNOT_BE_READ(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "업로드한 파일을 읽을 수 없습니다.")),
    NO_FILE_UPLOADED(ErrorStatus.of(HttpStatus.BAD_REQUEST, "파일명과 현재 입사자 이름이 일치하지 않아 파일이 업로드되지 않았습니다.")),
    FILE_NOT_FOUND(ErrorStatus.of(HttpStatus.BAD_REQUEST, "해당 파일이 존재하지 않습니다.")),
    FILE_CANNOT_BE_DELETED(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 삭제할 수 없습니다.")),
    FOLDER_CANNOT_BE_DELETED(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "재귀적으로 파일을 삭제하는 과정에서 오류가 발생했습니다.")),
    FOLDER_CANNOT_BE_CREATED(ErrorStatus.of(HttpStatus.INTERNAL_SERVER_ERROR, "폴더를 생성하는 과정에서 오류가 발생했습니다."))
    ;

    private final ErrorStatus errorStatus;

    FileErrorCode(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }


    @Override
    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
