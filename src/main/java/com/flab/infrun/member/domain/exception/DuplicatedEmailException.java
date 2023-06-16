package com.flab.infrun.member.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class DuplicatedEmailException extends SystemException {

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
