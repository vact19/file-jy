package com.gmdrive.gmdrive.global.exception.dto;

import com.gmdrive.gmdrive.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final int httpCode;
    private final String errorMessage;

    public ErrorResponse(HttpStatus httpStatus, String errorMessage) {
        this.httpCode = httpStatus.value();
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.httpCode = errorCode.getErrorStatus().httpCode;
        this.errorMessage = errorCode.getErrorStatus().message;
    }
}
