package com.flab.infrun.order.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.infrun.order.application.result.CreatedOrderResult;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

public record CreatedOrderResponse(

    Long orderId,
    String totalPrice,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime orderDate,
    String orderStatus,
    List<CreatedOrderItemResponse> orderItems,
    boolean isCouponApplied
) {

    public static CreatedOrderResponse from(final CreatedOrderResult result) {
        return new CreatedOrderResponse(
            result.orderId(),
            new DecimalFormat("###,###").format(result.totalPrice()),
            result.orderedAt(),
            result.orderStatus(),
            result.orderItems().stream()
                .map(CreatedOrderItemResponse::from)
                .toList(),
            result.isCouponApplied()
        );
    }

    public record CreatedOrderItemResponse(
        String lectureName,
        String basePrice,
        String salesPrice
    ) {

        public static CreatedOrderItemResponse from(
            final CreatedOrderResult.OrderItemResult orderItem) {
            return new CreatedOrderItemResponse(
                orderItem.lectureName(),
                new DecimalFormat("###,###").format(orderItem.basePrice()),
                new DecimalFormat("###,###").format(orderItem.salesPrice())
            );
        }
    }
}
