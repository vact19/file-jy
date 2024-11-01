package com.gmdrive.gmdrive.global.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

// 401 에러 핸들러를 구현.
@RequiredArgsConstructor
@Component
public class Custom401AuthEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationFilterErrorResponder authenticationFilterErrorResponder;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        authenticationFilterErrorResponder.respond(request, response);
    }
}
