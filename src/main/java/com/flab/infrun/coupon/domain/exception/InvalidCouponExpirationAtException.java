package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidCouponExpirationAtException extends SystemException {

    public InvalidCouponExpirationAtException() {
        super(ErrorCode.INVALID_COUPON_EXPIRATION_AT);
    }
}
