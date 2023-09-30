package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class NotFoundCouponException extends SystemException {

    public NotFoundCouponException() {
        super(ErrorCode.NOT_FOUND_COUPON);
    }
}
