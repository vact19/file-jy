package com.file_jy.domain.jwt.service;


import com.file_jy.domain.jwt.constant.AuthScheme;
import com.file_jy.domain.jwt.constant.TokenType;
import com.file_jy.global.error.errorcode.AuthErrorCode;
import com.file_jy.global.error.exception.auth.jwt.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class TokenValidator {

    private final TokenManager tokenManager;

    /**
     * @return userId
     */
    public long validateToken(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
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

        String token = authorizations[1]; // Bearer 뒤의 토큰 몸통 부분
        // 3. 토큰 유효성(변조) 검사
        if (! tokenManager.validateToken(token)) {
            throw new JwtAuthenticationException(AuthErrorCode.NOT_VALID_TOKEN);
        }

        //  4. 토큰 타입 검증
        String tokenType = tokenManager.getTokenType(token);
        if(!TokenType.ACCESS.name().equals(tokenType)) { // ACCESS 토큰이 아니면
            throw new JwtAuthenticationException(AuthErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        Claims claims = tokenManager.getTokenClaims(token);
        // 5. 토큰 만료 검사
        if (tokenManager.isTokenExpired(claims.getExpiration())) {
            throw new JwtAuthenticationException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        return Long.parseLong(claims.getAudience());
    }
}
