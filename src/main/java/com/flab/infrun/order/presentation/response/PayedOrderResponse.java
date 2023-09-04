package com.flab.infrun.order.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.infrun.order.application.result.PayedOrderResult;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PayedOrderResponse(
    BigDecimal amount,
    String payType,
    String payMethod,
    String orderStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime payedAt
) {

    public static PayedOrderResponse from(final PayedOrderResult result) {
        return new PayedOrderResponse(
            result.amount(),
            result.payType().getDescription(),
            result.payMethod().getDescription(),
            result.orderStatus(),
            result.payedAt()
        );
    }
}
