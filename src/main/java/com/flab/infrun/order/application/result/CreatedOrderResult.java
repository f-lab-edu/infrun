package com.flab.infrun.order.application.result;

import com.flab.infrun.order.domain.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatedOrderResult(
    Long orderId,
    BigDecimal totalPrice,
    LocalDateTime orderedAt,
    String orderStatus,
    boolean isCouponApplied
) {

    public static CreatedOrderResult from(
        final Order order
    ) {
        return new CreatedOrderResult(
            order.getId(),
            order.getTotalPrice(),
            order.getCreatedAt(),
            order.getOrderStatus().getDescription(),
            order.isCouponApplied()
        );
    }
}
