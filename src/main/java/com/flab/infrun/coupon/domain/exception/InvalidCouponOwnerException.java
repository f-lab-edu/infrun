package com.flab.infrun.coupon.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class InvalidCouponOwnerException extends SystemException {

    public InvalidCouponOwnerException() {
        super(ErrorCode.INVALID_COUPON_OWNER);
    }
}
