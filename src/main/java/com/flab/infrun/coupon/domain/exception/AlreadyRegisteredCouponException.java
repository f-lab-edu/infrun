package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class AlreadyRegisteredCouponException extends SystemException {

    public AlreadyRegisteredCouponException() {
        super(ErrorCode.ALREADY_REGISTERED_COUPON);
    }
}
