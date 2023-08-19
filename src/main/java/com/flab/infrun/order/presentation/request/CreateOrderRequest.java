package com.flab.infrun.order.presentation.request;

import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.command.CreateOrderCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(
    @NotEmpty(message = "강의 ID는 필수입니다.")
    @NotNull(message = "강의 ID는 필수입니다.")
    List<Long> lectureIds,
    String couponCode
) {

    public CreateOrderCommand toCommand(final Member customer) {
        return new CreateOrderCommand(
            customer,
            lectureIds,
            couponCode
        );
    }
}
