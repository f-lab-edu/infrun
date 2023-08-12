package com.flab.infrun.cart.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;

public final class NotFoundCartItemException extends CartException {

    public NotFoundCartItemException() {
        super(ErrorCode.NOT_FOUND_CART_ITEM);
    }
}
