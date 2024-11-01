package com.gmdrive.gmdrive.global.config.security;


import com.gmdrive.gmdrive.domain.jwt.constant.AuthScheme;
import com.gmdrive.gmdrive.domain.jwt.constant.TokenType;
import com.gmdrive.gmdrive.domain.jwt.service.TokenManager;
import com.gmdrive.gmdrive.global.error.errorcode.AuthErrorCode;
import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        verifyTokenAndSetAuthentication(request, header);

        filterChain.doFilter(request, response);
    }

    private void verifyTokenAndSetAuthentication(HttpServletRequest request, String authHeader){
        //  1. 토큰 유무 확인
        if(!StringUtils.hasText(authHeader)){
            request.setAttribute(ErrorCode.class.getSimpleName(), AuthErrorCode.NOT_EXISTS_AUTH_HEADER);
            return;
        }

        //  2. authorization Bearer 체크
        String[] authorizations = authHeader.split(" ");
        // AuthScheme.BEARER.getType() 은 "Bearer"문자열 반환
        if(authorizations.length < 2 || (!AuthScheme.BEARER.getLabel().equals(authorizations[0]))){
            request.setAttribute(ErrorCode.class.getSimpleName(), AuthErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
            return;
        }

        String token = authorizations[1]; // Bearer 뒤의 토큰 몸통 부분
        // 3. 토큰 유효성(변조) 검사
        if (! tokenManager.validateToken(token)) {
            request.setAttribute(ErrorCode.class.getSimpleName(), AuthErrorCode.NOT_VALID_TOKEN);
            return;
        }

        //  4. 토큰 타입 검증
        String tokenType = tokenManager.getTokenType(token);
        if(!TokenType.ACCESS.name().equals(tokenType)) { // ACCESS 토큰이 아니면
            request.setAttribute(ErrorCode.class.getSimpleName(), AuthErrorCode.NOT_ACCESS_TOKEN_TYPE);
            return;
        }

        Claims claims = tokenManager.getTokenClaims(token);
        // 5. 토큰 만료 검사
        if (tokenManager.isTokenExpired(claims.getExpiration())) {
            request.setAttribute(ErrorCode.class.getSimpleName(), AuthErrorCode.ACCESS_TOKEN_EXPIRED);
            return;
        }

        String audience = claims.getAudience();
        // 마지막으로 인증 정보로 Audience  저장
        Authentication authentication = new UsernamePasswordAuthenticationToken(audience, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
