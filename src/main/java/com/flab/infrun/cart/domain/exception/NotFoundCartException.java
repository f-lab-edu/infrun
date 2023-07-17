package com.flab.infrun.cart.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class NotFoundCartException extends SystemException {

    public NotFoundCartException() {
        super(ErrorCode.NOT_FOUND_CART);
    }
}
