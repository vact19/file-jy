package com.gmdrive.gmdrive.global.error.exception.auth.security;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.error.exception.auth.HttpAuthenticationException;

public class AnonymousUserException extends HttpAuthenticationException {
    public AnonymousUserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AnonymousUserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
