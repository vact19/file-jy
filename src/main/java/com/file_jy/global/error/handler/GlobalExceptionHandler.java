package com.file_jy.global.error.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.file_jy.global.error.dto.ErrorResponse;
import com.file_jy.global.error.errorcode.ErrorCode;
import com.file_jy.global.error.errorcode.HttpErrorCode;
import com.file_jy.global.error.exception.HttpException;
import com.file_jy.global.util.log.LoggingUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private final ObjectMapper objectMapper;
    private final LoggingUtil loggingUtil; 
    
    /**
     * \@ModelAttribute 으로 binding error 발생시 BindException 발생한다.
     * \@RequestBody @Valid 바인딩 오류(HttpMessageConverter binding)시 ConstraintViolationException 을 추상화한
     *  MethodArgumentNotValidException 도 BindException 을 확장한다.
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e, HttpServletRequest request) throws JsonProcessingException {
        loggingUtil.printLog(request, e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        StringBuilder sb = new StringBuilder();
        Map<String, String> errorInfoMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            String errorMsg = sb
                    .append(fieldError.getDefaultMessage())
                    .append(" - 요청받은 값: ")
                    .append(fieldError.getRejectedValue())
                    .toString();

            errorInfoMap.put(fieldError.getField(), errorMsg);

            sb.setLength(0);
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, objectMapper.writeValueAsString(errorInfoMap));
    }

    /** spring handler @RequestParam 파라미터 누락*/
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        loggingUtil.printLog(request, e);
        String message = "파라미터 '" + e.getParameterName() + "'이 누락되었습니다.";
        return createErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestPartException e, HttpServletRequest request) {
        loggingUtil.printLog(request, e);
        String message = "파라미터 '" + e.getRequestPartName() + "'이 누락되었습니다.";
        return createErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(Exception e, HttpServletRequest request){
        loggingUtil.printLog(request, e);
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // ENUM 변환실패, 날짜타입에 2999-15-99 와 같은 잘못된 값이 들어올 때 발생
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(HttpMessageNotReadableException e, HttpServletRequest request){
        loggingUtil.printLog(request, e);
        return createErrorResponse(HttpErrorCode.HTTP_MESSAGE_NOT_READABLE);
    }

    // global.exception.exceptions 패키지의 커스텀 예외 처리를 담당한다.
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(HttpException e, HttpServletRequest request){
        loggingUtil.printLog(request, e);
        return createErrorResponse(HttpStatus.valueOf(e.httpCode), e.getMessage());
    }

    // 예상하지 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
        log.error("예외처리 범위 외의 오류 발생.");
        loggingUtil.printLog(request, e);

        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private  ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus httpStatus, String errorMessage) {
        ErrorResponse errDto = new ErrorResponse(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(errDto);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        HttpStatus httpStatus = HttpStatus.valueOf(errorCode.getErrorStatus().httpCode);
        ErrorResponse errDto = new ErrorResponse(httpStatus, errorCode.getErrorStatus().message);
        return ResponseEntity.status(httpStatus).body(errDto);
    }
}
