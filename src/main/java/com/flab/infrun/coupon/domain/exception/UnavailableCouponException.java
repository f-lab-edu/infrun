package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class UnavailableCouponException extends SystemException {

    public UnavailableCouponException() {
        super(ErrorCode.UNAVAILABLE_COUPON);
    }
}
