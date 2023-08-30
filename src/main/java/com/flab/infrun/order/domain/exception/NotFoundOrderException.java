package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class NotFoundOrderException extends SystemException {

    public NotFoundOrderException() {
        super(ErrorCode.NOT_FOUND_ORDER);
    }
}
