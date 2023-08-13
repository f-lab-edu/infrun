package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidCouponQuantityException extends SystemException {

    public InvalidCouponQuantityException() {
        super(ErrorCode.INVALID_COUPON_QUANTITY);
    }
}
