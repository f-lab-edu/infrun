package com.flab.infrun.coupon.presentation.request;

import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.member.domain.Member;
import jakarta.validation.constraints.NotBlank;

public record EnrollCouponRequest(
    @NotBlank(message = "쿠폰 코드는 필수입니다.")
    String couponCode
) {

    public CouponRegisterCommand toCommand(final Member member) {
        return new CouponRegisterCommand(member, couponCode);
    }
}
