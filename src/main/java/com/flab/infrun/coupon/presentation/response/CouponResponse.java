package com.flab.infrun.coupon.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import java.time.LocalDateTime;
import java.util.List;

public record CouponResponse(
    int quantity,
    List<String> couponCodes,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime expirationAt
) {

    public static CouponResponse from(final CreatedCouponResult createdCouponResult) {
        return new CouponResponse(
            createdCouponResult.quantity(),
            createdCouponResult.couponCodes(),
            createdCouponResult.expirationAt()
        );
    }
}
