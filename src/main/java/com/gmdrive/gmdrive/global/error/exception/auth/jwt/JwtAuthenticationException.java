package com.gmdrive.gmdrive.global.error.exception.auth.jwt;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import com.gmdrive.gmdrive.global.error.exception.auth.HttpAuthenticationException;

public class JwtAuthenticationException extends HttpAuthenticationException {
    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
    public JwtAuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
