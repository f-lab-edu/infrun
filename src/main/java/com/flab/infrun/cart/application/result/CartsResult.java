package com.flab.infrun.cart.application.result;

import java.math.BigDecimal;
import java.util.List;

public record CartsResult(
    List<CartItemResult> cartItemsResults,
    BigDecimal totalPrice
) {

    public record CartItemResult(
        Long lectureId,
        String lectureTitle,
        String teacherName,
        BigDecimal price
    ) {

    }
}
