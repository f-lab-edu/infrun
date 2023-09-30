package com.flab.infrun.order.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public final class OrderPayAmountNotMatchException extends SystemException {

    public OrderPayAmountNotMatchException() {
        super(ErrorCode.ORDER_PAY_AMOUNT_NOT_MATCH);
    }
}
