package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class AlreadyCompletedOrderException extends SystemException {

    public AlreadyCompletedOrderException() {
        super(ErrorCode.ALREADY_COMPLETED_ORDER);
    }
}
