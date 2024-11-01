package com.gmdrive.gmdrive.global.error.handler;

import com.gmdrive.gmdrive.global.error.dto.ErrorResponse;
import com.gmdrive.gmdrive.global.util.log.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DefaultErrorHandler implements ErrorController {

    private final ErrorAttributes errorAttributes;
    private final LoggingUtil loggingUtil;

    @GetMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(new ServletWebRequest(request), ErrorAttributeOptions.defaults());
        Throwable error = errorAttributes.getError(new ServletWebRequest(request));

        log.error("예외처리 범위 외의 오류 발생 - ErrorController");

        String errorMsg;
        if (error != null) {
            loggingUtil.printLog(request, error);
            errorMsg = error.getMessage();
        } else {
            errorMsg = (String) errorAttributesMap.getOrDefault("message", "Unknown error");
        }

        return createErrorResponse(errorMsg);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String errorMessage) {
        ErrorResponse errDto = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return ResponseEntity.status(errDto.getHttpCode()).body(errDto);
    }
}
