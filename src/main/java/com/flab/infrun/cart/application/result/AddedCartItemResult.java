package com.flab.infrun.cart.application.result;

import java.math.BigDecimal;
import java.util.List;

public record AddedCartItemResult(
    BigDecimal totalPrice,
    List<Long> lectureIds
) {

    public static AddedCartItemResult from(
        final BigDecimal totalPrice,
        final List<Long> lectureIds
    ) {
        return new AddedCartItemResult(totalPrice, lectureIds);
    }
}
