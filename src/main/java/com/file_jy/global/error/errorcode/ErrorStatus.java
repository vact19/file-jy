package com.file_jy.global.error.errorcode;

import org.springframework.http.HttpStatus;

public class ErrorStatus {
    public final int httpCode;
    public final String message;

    private ErrorStatus(int httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }

    public static ErrorStatus of(HttpStatus httpCode, String message) {
        return new ErrorStatus(httpCode.value(), message);
    }
}
