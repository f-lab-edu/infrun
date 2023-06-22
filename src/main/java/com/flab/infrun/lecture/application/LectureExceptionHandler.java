package com.flab.infrun.lecture.application;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;
import com.flab.infrun.common.response.Response;
import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class LectureExceptionHandler {

    @ExceptionHandler(DuplicateLectureFileNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<ErrorCode> handleException(SystemException e) {
        return Response.fail(e.getErrorCode());
    }
}
