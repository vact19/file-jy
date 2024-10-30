package com.gmdrive.gmdrive.global.exception.exceptions.business;

import com.gmdrive.gmdrive.global.exception.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.exception.exceptions.HttpException;
import org.springframework.http.HttpStatus;

// 비즈니스 로직 상 예외
public class BusinessException extends HttpException {

    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, Object source) {
        super(errorCode, source);
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public BusinessException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}
