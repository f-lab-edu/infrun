package com.flab.infrun.order.application.result;

import com.flab.infrun.order.domain.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateOrderResult(
    Long orderId,
    BigDecimal totalPrice,
    LocalDateTime orderedAt,
    String orderStatus
) {

    public static CreateOrderResult from(
        final Order order
    ) {
        return new CreateOrderResult(
            order.getId(),
            order.getTotalPrice(),
            order.getCreatedAt(),
            order.getOrderStatus().getDescription()
        );
    }
}
