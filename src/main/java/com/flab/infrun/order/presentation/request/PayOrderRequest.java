package com.flab.infrun.order.presentation.request;

import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.command.PayOrderCommand;
import com.flab.infrun.payment.domain.PayMethod;
import com.flab.infrun.payment.domain.PayType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record PayOrderRequest(

    @NotNull(message = "주문 ID는 필수입니다.")
    @Min(value = 1, message = "주문 ID는 1 이상이어야 합니다.")
    Long orderId,
    @NotNull(message = "결제 금액은 필수입니다.")
    BigDecimal amount,
    @NotBlank(message = "결제 방법은 필수입니다.")
    @Pattern(regexp = "\\bCARD\\b|\\bCASH\\b", message = "결제 방법은 CARD 또는 CASH만 가능합니다.")
    String payMethod,

    @NotBlank(message = "결제 타입은 필수입니다.")
    @Pattern(regexp = "\\bLUMP_SUM\\b|\\bINSTALLMENT\\b", message = "결제 타입은 LUMP_SUM 또는 INSTALLMENT만 가능합니다.")
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
