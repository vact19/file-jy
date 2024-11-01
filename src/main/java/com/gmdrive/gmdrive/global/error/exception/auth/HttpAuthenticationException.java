package com.gmdrive.gmdrive.global.error.exception.auth;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.error.exception.HttpException;

public class HttpAuthenticationException extends HttpException {

    public HttpAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public HttpAuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
