package com.gmdrive.gmdrive.global.error.exception;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {
    public final int httpCode;

    protected HttpException(ErrorCode errorCode) {
        super(errorCode.getErrorStatus().message);
        this.httpCode = errorCode.getErrorStatus().httpCode;
    }

    // 로깅에 필요한 입력값을 받아 Exception 메시지에 함께 전달
    protected HttpException(ErrorCode errorCode, Object source) {
        super(String.format(
                "%s. source -> %s", errorCode.getErrorStatus().message
                , source == null ? "null" : source.toString()
                )
        );
        this.httpCode = errorCode.getErrorStatus().httpCode;
    }

    protected HttpException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrorStatus().message, cause);
        this.httpCode = errorCode.getErrorStatus().httpCode;
    }

    protected HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpCode = httpStatus.value();
    }

    protected HttpException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpCode = httpStatus.value();
    }
}
