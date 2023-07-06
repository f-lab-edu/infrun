package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class InvalidCouponDiscountTypeException extends SystemException {

    public InvalidCouponDiscountTypeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
