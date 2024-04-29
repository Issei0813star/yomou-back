package com.yomou.handler;

import com.yomou.exception.YomouException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class YomouExceptionHandler {

    @ExceptionHandler(YomouException.class)
    public ResponseEntity<Map<String, Object>> handleYomouException(YomouException e){
        return ResponseEntity.status(e.getStatus()).body(Map.of("errorMessage", e.getMessage(), "causedBy", e.getCausedBy(), "errorCode", e.getErrorCode()));
    }
}
