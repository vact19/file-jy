package com.gmdrive.gmdrive.global.exception.exceptions.auth;

import com.gmdrive.gmdrive.global.exception.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.exception.exceptions.HttpException;

public class HttpAuthenticationException extends HttpException {

    public HttpAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public HttpAuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
