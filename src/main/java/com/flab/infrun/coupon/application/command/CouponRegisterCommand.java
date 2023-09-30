package com.flab.infrun.coupon.application.command;

import com.flab.infrun.member.domain.Member;

public record CouponRegisterCommand(
    Member member,
    String couponCode
) {

}
