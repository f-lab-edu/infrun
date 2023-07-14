package com.flab.infrun.cart.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class NoCartItemsException extends SystemException {

    public NoCartItemsException() {
        super(ErrorCode.NO_CART_ITEMS);
    }
}
