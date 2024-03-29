package com.ssafy.dmobile.common.handler;

import com.ssafy.dmobile.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static com.ssafy.dmobile.common.exception.ExceptionType.MISSING_HEADER_EXCEPTION;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(int code, String message, HttpStatus status) {

        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("msg", message);

        return ResponseEntity.status(status).body(response);
    }

    // 핸들링 가능한 오류에 대한 Exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.error("handleCustomException throw Exception : {}" + e.getMessage(), e.getExceptionType());
        return buildResponseEntity(100, e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeader(MissingRequestHeaderException ex) {
        CustomException e = new CustomException(MISSING_HEADER_EXCEPTION);
        log.error("handleCustomException throw Exception : {}" + e.getMessage(), e.getExceptionType());

        return buildResponseEntity(200, e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("unhandledException throw Exception : {}", e.getMessage());
        return buildResponseEntity(500, "예상치 못한 에러가 발생하였습니다.<br>" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
