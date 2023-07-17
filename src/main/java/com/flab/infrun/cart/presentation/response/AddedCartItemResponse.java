package com.flab.infrun.cart.presentation.response;

import com.flab.infrun.cart.application.result.AddedCartItemResult;
import java.math.BigDecimal;
import java.util.List;

public record AddedCartItemResponse(
    BigDecimal totalPrice,
    List<Long> lectureIds
) {

    public static AddedCartItemResponse from(final AddedCartItemResult result) {
        return new AddedCartItemResponse(result.totalPrice(), result.lectureIds());
    }
}
