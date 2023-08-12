package com.flab.infrun.cart.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class CartException extends SystemException {

    public CartException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
