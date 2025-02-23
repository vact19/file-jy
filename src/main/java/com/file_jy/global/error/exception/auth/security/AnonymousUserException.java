package com.file_jy.global.error.exception.auth.security;

import com.file_jy.global.error.errorcode.ErrorCode;
import com.file_jy.global.error.exception.auth.HttpAuthenticationException;

public class AnonymousUserException extends HttpAuthenticationException {
    public AnonymousUserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AnonymousUserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
