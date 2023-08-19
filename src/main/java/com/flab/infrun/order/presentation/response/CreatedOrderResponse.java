package com.flab.infrun.order.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.infrun.order.application.result.CreatedOrderResult;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

public record CreatedOrderResponse(

    Long orderId,
    String totalPrice,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime orderDate,
    String orderStatus,
    boolean isCouponApplied
) {

    public static CreatedOrderResponse from(final CreatedOrderResult result) {
        return new CreatedOrderResponse(
            result.orderId(),
            new DecimalFormat("###,###").format(result.totalPrice()),
            result.orderedAt(),
            result.orderStatus(),
            result.isCouponApplied()
        );
    }
}
