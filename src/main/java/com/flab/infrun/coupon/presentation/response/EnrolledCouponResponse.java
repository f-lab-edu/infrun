package com.flab.infrun.coupon.presentation.response;

import com.flab.infrun.coupon.application.result.EnrolledCouponResult;
import java.time.LocalDateTime;

public record EnrolledCouponResponse(
    String ownerEmail,
    String discountType,
    int discountValue,
    LocalDateTime expirationAt
) {

    public static EnrolledCouponResponse from(final EnrolledCouponResult result) {
        return new EnrolledCouponResponse(
            result.ownerEmail(),
            result.discountInfo()
                .getDiscountType()
                .name(),
            result.discountInfo()
                .getDiscountValue(),
            result.expirationAt()
        );
    }
}
