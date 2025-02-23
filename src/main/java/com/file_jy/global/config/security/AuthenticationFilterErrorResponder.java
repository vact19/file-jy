package com.file_jy.global.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.file_jy.global.error.dto.ErrorResponse;
import com.file_jy.global.error.errorcode.ErrorCode;
import com.file_jy.global.util.log.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class AuthenticationFilterErrorResponder {
    private final ObjectMapper objectMapper;
    private final LoggingUtil loggingUtil;

    public void respond(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute(ErrorCode.class.getSimpleName());
        HttpStatus httpStatus = HttpStatus.valueOf(errorCode.getErrorStatus().httpCode);

        ErrorResponse errDto = new ErrorResponse(
                httpStatus
                , errorCode.getErrorStatus().message
        );
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        loggingUtil.printLog(request, errorCode);

        objectMapper.writeValue(response.getWriter(), errDto);
    }
}
