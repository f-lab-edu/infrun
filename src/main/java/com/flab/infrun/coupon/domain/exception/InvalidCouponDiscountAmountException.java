package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidCouponDiscountAmountException extends SystemException {

    public InvalidCouponDiscountAmountException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
