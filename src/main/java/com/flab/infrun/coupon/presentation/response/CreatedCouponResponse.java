package com.flab.infrun.coupon.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import java.time.LocalDateTime;
import java.util.List;

public record CreatedCouponResponse(
    int quantity,
    List<String> couponCodes,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime expirationAt
) {

    public static CreatedCouponResponse from(final CreatedCouponResult createdCouponResult) {
        return new CreatedCouponResponse(
            createdCouponResult.quantity(),
            createdCouponResult.couponCodes(),
            createdCouponResult.expirationAt()
        );
    }
}
