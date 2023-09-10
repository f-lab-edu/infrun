package com.flab.infrun.order.presentation.request;

import com.flab.infrun.common.validator.EnumValid;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.command.PayOrderCommand;
import com.flab.infrun.payment.domain.PayMethod;
import com.flab.infrun.payment.domain.PayType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PayOrderRequest(

    @NotNull(message = "주문 ID는 필수입니다.")
    @Min(value = 1, message = "주문 ID는 1 이상이어야 합니다.")
    Long orderId,
    @NotNull(message = "결제 금액은 필수입니다.")
    BigDecimal amount,
    @NotBlank(message = "결제 방법은 필수입니다.")
    @EnumValid(type = PayMethod.class, message = "결제 방법은 CARD 또는 CASH만 가능합니다.", ignoreCase = true)
    String payMethod,

    @NotBlank(message = "결제 타입은 필수입니다.")
    @EnumValid(type = PayType.class, message = "결제 타입은 LUMP_SUM 또는 INSTALLMENT만 가능합니다.", ignoreCase = true)
    String payType
) {

    public PayOrderCommand toCommand(final Member member) {
        return new PayOrderCommand(
            member.getId(),
            1L,
            BigDecimal.valueOf(40_000),
            PayMethod.valueOf(payMethod),
            PayType.valueOf(payType)
        );
    }
}
