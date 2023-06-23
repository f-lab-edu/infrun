package com.flab.infrun.common;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.response.Response;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public Response<ErrorCode> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();

        if (Objects.isNull(fieldError)) {
            return Response.fail(ErrorCode.INVALID_PARAMETER);
        }

        final String sb = "Request Error"
            + " " + fieldError.getField()
            + " " + fieldError.getDefaultMessage();

        return Response.fail(ErrorCode.INVALID_PARAMETER, sb);
    }
}
