package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class AlreadyCanceledOrderException extends SystemException {

    public AlreadyCanceledOrderException() {
        super(ErrorCode.ALREADY_CANCELED_ORDER);
    }
}
