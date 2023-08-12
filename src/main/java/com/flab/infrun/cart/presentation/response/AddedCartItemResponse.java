package com.flab.infrun.cart.presentation.response;

import com.flab.infrun.cart.application.result.AddedCartItemResult;
import java.util.List;

public record AddedCartItemResponse(
    List<Long> lectureIds
) {

    public static AddedCartItemResponse from(final AddedCartItemResult result) {
        return new AddedCartItemResponse(result.lectureIds());
    }
}
