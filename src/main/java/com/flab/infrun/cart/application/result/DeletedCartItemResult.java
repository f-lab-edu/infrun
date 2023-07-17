package com.flab.infrun.cart.application.result;

import java.math.BigDecimal;
import java.util.List;

public record DeletedCartItemResult(
    BigDecimal totalPrice,
    List<Long> lectureIds
) {

    public static DeletedCartItemResult from(
        final BigDecimal totalPrice,
        final List<Long> lectureIds
    ) {
        return new DeletedCartItemResult(totalPrice, lectureIds);
    }
}
