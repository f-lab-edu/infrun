package com.flab.infrun.cart.application.result;

import java.util.List;

public record AddedCartItemResult(
    List<Long> lectureIds
) {

    public static AddedCartItemResult from(
        final List<Long> lectureIds
    ) {
        return new AddedCartItemResult(lectureIds);
    }
}
