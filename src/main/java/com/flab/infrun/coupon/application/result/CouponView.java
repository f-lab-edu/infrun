package com.flab.infrun.coupon.application.result;

public record CouponView(
    long dDay,
    int discountValue,
    String discountType
) {


}
