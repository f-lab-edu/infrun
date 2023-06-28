package com.flab.infrun.member.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidAuthenticationException extends SystemException {

    public InvalidAuthenticationException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
