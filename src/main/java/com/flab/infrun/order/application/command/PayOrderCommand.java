package com.flab.infrun.order.application.command;

import com.flab.infrun.payment.domain.PayMethod;
import com.flab.infrun.payment.domain.PayType;
import java.math.BigDecimal;

public record PayOrderCommand(
    Long userId,
    Long orderId,
    BigDecimal amount,
    PayMethod payMethod,
    PayType payType,
    Integer installmentMonths
) {

}

