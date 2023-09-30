package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidPriceValueException extends SystemException {

    public InvalidPriceValueException() {
        super(ErrorCode.INVALID_PRICE_VALUE);
    }
}
