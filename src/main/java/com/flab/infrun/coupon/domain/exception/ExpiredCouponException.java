package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class ExpiredCouponException extends SystemException {

    public ExpiredCouponException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
