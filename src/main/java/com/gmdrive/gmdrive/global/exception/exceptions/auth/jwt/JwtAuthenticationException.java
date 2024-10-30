package com.gmdrive.gmdrive.global.exception.exceptions.auth.jwt;

import com.gmdrive.gmdrive.global.exception.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.exception.exceptions.auth.HttpAuthenticationException;

public class JwtAuthenticationException extends HttpAuthenticationException {
    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
    public JwtAuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
