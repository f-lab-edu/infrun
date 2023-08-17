package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class InvalidOrderItemPriceException extends SystemException {

    public InvalidOrderItemPriceException() {
        super(ErrorCode.INVALID_ORDER_ITEM_PRICE_VALUE);
    }
}
