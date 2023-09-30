package com.flab.infrun.cart.presentation.request;

import com.flab.infrun.cart.application.command.AddCartItemCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest(

    @NotNull(message = "강의 ID는 필수입니다.")
    @Min(value = 1, message = "강의 ID는 1 이상이어야 합니다.")
    Long lectureId
) {

    public AddCartItemCommand toCommand(final Long memberId) {
        return new AddCartItemCommand(memberId, lectureId);
    }
}
