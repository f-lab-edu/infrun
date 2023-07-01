package com.flab.infrun.coupon.presentation.request;

import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record CreateCouponRequest(
    @NotBlank(message = "할인 타입은 필수 입력입니다.")
    String discountType,
    @Positive(message = "할인 금액 또는 할인율은 필수 입력입니다.")
    int discountAmount,
    @NotNull(message = "쿠폰 만료일은 필수 입력입니다.")
    LocalDateTime expirationAt,
    @Positive(message = "쿠폰 수량은 1개 이상이어야 합니다.")
    int quantity
) {

    public CreateCouponCommand toCommand() {
        return new CreateCouponCommand(
            discountType,
            discountAmount,
            expirationAt,
            quantity
        );
    }
}
