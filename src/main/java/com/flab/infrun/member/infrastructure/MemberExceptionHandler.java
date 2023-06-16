package com.flab.infrun.member.infrastructure;

import com.flab.infrun.common.exception.SystemException;
import com.flab.infrun.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> handleException(SystemException e) {
        log.error("SystemError: {}", e.getMessage());

        return ResponseEntity
            .status(400)
            .body(Response.fail(e.getErrorCode(), e.getErrorCode().getMessage()));
    }
}
