package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidCouponDiscountTypeException extends SystemException {

    public InvalidCouponDiscountTypeException() {
        super(ErrorCode.INVALID_COUPON_DISCOUNT_TYPE);
    }
}
