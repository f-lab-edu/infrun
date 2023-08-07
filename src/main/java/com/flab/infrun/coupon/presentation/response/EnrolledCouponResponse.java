package com.flab.infrun.coupon.presentation.response;

import com.flab.infrun.coupon.application.result.EnrolledCouponResult;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnrolledCouponResponse(
    String ownerEmail,
    String discountType,
    BigDecimal discountValue,
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
