package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class InvalidCouponExpirationAtException extends SystemException {

    public InvalidCouponExpirationAtException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
