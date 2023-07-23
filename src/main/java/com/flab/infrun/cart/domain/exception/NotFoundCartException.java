package com.flab.infrun.cart.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;

public class NotFoundCartException extends CartException {

    public NotFoundCartException() {
        super(ErrorCode.NOT_FOUND_CART);
    }
}
