package com.flab.infrun.member.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class NotMatchPasswordException extends SystemException {

    public NotMatchPasswordException() {
        super(ErrorCode.NOT_MATCH_PASSWORD);
    }
}
