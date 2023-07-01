package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class InvalidCouponQuantityException extends SystemException {

    public InvalidCouponQuantityException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
