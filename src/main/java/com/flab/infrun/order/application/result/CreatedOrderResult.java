package com.flab.infrun.order.application.result;

import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreatedOrderResult(
    Long orderId,
    BigDecimal totalPrice,
    BigDecimal discountPrice,
    LocalDateTime orderedAt,
    String orderStatus,
    List<OrderItemResult> orderItems,
    boolean isCouponApplied
) {

    public static CreatedOrderResult from(
        final Order order
    ) {
        return new CreatedOrderResult(
            order.getId(),
            order.getTotalPrice(),
            order.getDiscountPrice(),
            order.getCreatedAt(),
            order.getOrderStatus().getDescription(),
            order.getOrderItems().stream()
                .map(OrderItemResult::from)
                .toList(),
            order.isCouponApplied()
        );
    }

    public record OrderItemResult(
        String teacherName,
        String lectureName,
        BigDecimal basePrice,
        BigDecimal salesPrice
    ) {

        public static OrderItemResult from(
            final OrderItem orderItem
        ) {
            return new OrderItemResult(
                orderItem.getProviderName(),
                orderItem.getItemName(),
                orderItem.getBasePrice(),
                orderItem.getSalesPrice());
        }
    }
}
