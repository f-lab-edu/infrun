package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidCreateOrderException extends SystemException {

    public InvalidCreateOrderException() {
        super(ErrorCode.INVALID_CREATE_ORDER);
    }
}
