package com.flab.infrun.cart.presentation.response;

import com.flab.infrun.cart.application.result.DeletedCartItemResult;
import java.util.List;

public record DeletedCartItemResponse(
    List<Long> lectureIds
) {

    public static DeletedCartItemResponse from(final DeletedCartItemResult result) {
        return new DeletedCartItemResponse(result.lectureIds());
    }
}
