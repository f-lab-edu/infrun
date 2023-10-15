package com.flab.infrun.member.infrastructure

import com.flab.infrun.common.exception.ErrorCode
import com.flab.infrun.common.exception.SystemException
import com.flab.infrun.common.response.Response
import com.flab.infrun.member.domain.exception.*
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class MemberExceptionHandler {
    @ExceptionHandler(
        DuplicatedNicknameException::class,
        DuplicatedEmailException::class,
        InvalidPasswordException::class,
        NotFoundMemberException::class,
        NotMatchPasswordException::class,
    )
    @ResponseStatus(
        HttpStatus.BAD_REQUEST
    )
    fun handleException(e: SystemException): Response<ErrorCode> {
        log.info(e.errorCode.message, e)

        return Response.fail(e.errorCode)
    }
    
    companion object {
        private val log = KotlinLogging.logger { }
    }
}
