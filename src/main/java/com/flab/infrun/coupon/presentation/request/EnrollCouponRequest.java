package com.flab.infrun.coupon.presentation.request;

import com.flab.infrun.common.config.security.UserAdapter;
import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import jakarta.validation.constraints.NotBlank;

public record EnrollCouponRequest(
    @NotBlank(message = "쿠폰 코드는 필수입니다.")
    String couponCode
) {

    public CouponRegisterCommand toCommand(final UserAdapter user) {
        return new CouponRegisterCommand(user.getMember(), couponCode);
    }
}
