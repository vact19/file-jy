package com.file_jy.global.error.exception.auth.jwt;

import com.file_jy.global.error.errorcode.ErrorCode;
import com.file_jy.global.error.exception.auth.HttpAuthenticationException;

public class JwtAuthenticationException extends HttpAuthenticationException {
    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
    public JwtAuthenticationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
