package com.file_jy.global.error.exception.auth;

import com.file_jy.global.error.errorcode.ErrorCode;
import com.file_jy.global.error.exception.HttpException;

public class HttpAuthenticationException extends HttpException {

    public HttpAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public HttpAuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
