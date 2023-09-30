package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class AlreadyUsedCouponException extends SystemException {

    public AlreadyUsedCouponException() {
        super(ErrorCode.ALREADY_USED_COUPON);
    }
}
