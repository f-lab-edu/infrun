package com.flab.infrun.coupon.presentation.response;

import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import java.time.LocalDateTime;
import java.util.List;

public record CouponResponse(
    int quantity,
    List<String> couponCodes,
    LocalDateTime expirationDate
) {

    public static CouponResponse from(final CreatedCouponResult createdCouponResult) {
        return new CouponResponse(
            createdCouponResult.quantity(),
            createdCouponResult.couponCodes(),
            createdCouponResult.expirationDate()
        );
    }
}
