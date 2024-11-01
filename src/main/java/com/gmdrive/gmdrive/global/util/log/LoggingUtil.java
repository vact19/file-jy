package com.gmdrive.gmdrive.global.util.log;

import com.gmdrive.gmdrive.global.error.errorcode.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingUtil {

    public void printLog(HttpServletRequest request, Throwable e) {
        log.error("발생 예외: {}, 에러 메시지: {}, 요청 Method: {}, 요청 url: {}",
                e.getClass().getSimpleName(), e.getMessage(), request.getMethod(), request.getRequestURI(), e);
    }

    public void printLog(HttpServletRequest request, ErrorCode errorCode) {
        log.error("에러 메시지: {}, 요청 Method: {}, 요청 url: {}",
                errorCode.getErrorStatus().message, request.getMethod(), request.getRequestURI());
    }
}
