package com.file_jy.domain.jwt.service;


import com.file_jy.domain.jwt.constant.AuthScheme;
import com.file_jy.global.error.errorcode.AuthErrorCode;
import com.file_jy.global.error.exception.auth.jwt.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class TokenValidator {
    public void validateBearer(String authHeader) {
        //  1. 토큰 유무 확인
        if(!StringUtils.hasText(authHeader)){
            throw new JwtAuthenticationException(AuthErrorCode.NOT_EXISTS_AUTH_HEADER);
        }

        //  2. authorization Bearer 체크
        String[] authorizations = authHeader.split(" ");
        // AuthScheme.BEARER.getType() 은 "Bearer"문자열 반환
        if(authorizations.length < 2 || (!AuthScheme.BEARER.getLabel().equals(authorizations[0]))){
            throw new JwtAuthenticationException(AuthErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }
}
