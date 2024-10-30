package com.gmdrive.gmdrive.global.exception.exceptions.auth.security;

import com.gmdrive.gmdrive.global.exception.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.exception.exceptions.auth.HttpAuthenticationException;

public class AnonymousUserException extends HttpAuthenticationException {
    public AnonymousUserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AnonymousUserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
