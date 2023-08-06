package com.flab.infrun.cart.presentation.response;

import com.flab.infrun.cart.application.result.CartsResult;
import java.math.BigDecimal;
import java.util.List;

public record CartsResponse(
    List<CartItem> carts,
    BigDecimal totalPrice
) {

    public static CartsResponse from(final CartsResult result) {
        return new CartsResponse(
            result.cartItemsResults().stream()
                .map(CartItem::from)
                .toList(), result.totalPrice()
        );
    }

    record CartItem(
        Long lectureId,
        String lectureTitle,
        String teacherName,
        BigDecimal price
    ) {

        public static CartItem from(final CartsResult.CartItemResult result) {
            return new CartItem(
                result.lectureId(),
                result.lectureTitle(),
                result.teacherName(),
                result.price()
            );
        }

    }
}
