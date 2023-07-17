package com.flab.infrun.cart.application.result;

import java.math.BigDecimal;
import java.util.List;

public record DeletedCartResult(
    BigDecimal totalPrice,
    List<Long> lectureIds
) {

    public static DeletedCartResult from(
        final BigDecimal totalPrice,
        final List<Long> lectureIds
    ) {
        return new DeletedCartResult(totalPrice, lectureIds);
    }
}
