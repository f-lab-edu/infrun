package com.flab.infrun.order.application.result;

import com.flab.infrun.order.domain.OrderStatus;
import com.flab.infrun.payment.domain.PayMethod;
import com.flab.infrun.payment.domain.PayType;
import com.flab.infrun.payment.domain.Payment;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PayedOrderResult(
    BigDecimal amount,
    PayType payType,
    PayMethod payMethod,
    String orderStatus,
    String payStatus,
    LocalDateTime payedAt
) {

    public static PayedOrderResult from(final Payment payment, final OrderStatus orderStatus) {
        return new PayedOrderResult(
            payment.getAmount(),
            payment.getPayType(),
            payment.getPayMethod(),
            orderStatus.getDescription(),
            payment.getPayStatus().getDescription(),
            payment.getCreatedAt());
    }
}
