package com.flab.infrun.coupon.application.result;

import java.time.LocalDateTime;
import java.util.List;

public record CreatedCouponResult(
    int quantity,
    List<String> couponCodes,
    LocalDateTime expirationAt
) {

    public static CreatedCouponResult from(
        final int quantity,
        final List<String> couponCodes,
        final LocalDateTime expirationAt
    ) {
        return new CreatedCouponResult(quantity, couponCodes, expirationAt);
    }
}
