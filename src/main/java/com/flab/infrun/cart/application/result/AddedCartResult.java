package com.flab.infrun.cart.application.result;

import java.math.BigDecimal;
import java.util.List;

public record AddedCartResult(
    BigDecimal totalPrice,
    List<Long> lectureIds
) {

    public static AddedCartResult from(
        final BigDecimal totalPrice,
        final List<Long> lectureIds
    ) {
        return new AddedCartResult(totalPrice, lectureIds);
    }
}
