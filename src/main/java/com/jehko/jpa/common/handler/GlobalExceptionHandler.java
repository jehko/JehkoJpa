package com.jehko.jpa.common.handler;

import com.jehko.jpa.common.exception.AuthFailException;
import com.jehko.jpa.common.model.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailException.class)
    public ResponseEntity<?> AuthFailException(AuthFailException exception) {
        return ResponseResult.fail(exception.getMessage());
    }

}
