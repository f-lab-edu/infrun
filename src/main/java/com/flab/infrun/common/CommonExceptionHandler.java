package com.flab.infrun.common;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<ErrorCode>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();

        StringBuilder sb = new StringBuilder()
            .append("Request Error")
            .append(" ").append(fieldError.getField())
            .append(" =").append(fieldError.getRejectedValue())
            .append(" ").append(fieldError.getDefaultMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Response.fail(ErrorCode.INVALID_PARAMETER, sb.toString()));
    }
}
