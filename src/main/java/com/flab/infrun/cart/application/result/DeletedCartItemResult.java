package com.flab.infrun.cart.application.result;

import java.util.List;

public record DeletedCartItemResult(
    List<Long> lectureIds
) {

    public static DeletedCartItemResult from(
        final List<Long> lectureIds
    ) {
        return new DeletedCartItemResult(lectureIds);
    }
}
