package com.gmdrive.gmdrive.global.error.dto;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    // 지나치게 자세한 메시지를 반환하지 않기 위한 글자 제한
    private static final int MAX_ERROR_MESSAGE_LENGTH = 70;

    private final int httpCode;
    private final String errorMessage;

    public ErrorResponse(HttpStatus httpStatus, String errorMessage) {
        if (errorMessage != null && errorMessage.length() > MAX_ERROR_MESSAGE_LENGTH) {
            errorMessage = errorMessage.substring(0, MAX_ERROR_MESSAGE_LENGTH) + "...";
        }
        this.httpCode = httpStatus.value();
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.httpCode = errorCode.getErrorStatus().httpCode;
        this.errorMessage = errorCode.getErrorStatus().message;
    }
}
