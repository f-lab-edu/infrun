package com.flab.infrun.coupon.application.result;

import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.DiscountInfo;
import java.time.LocalDateTime;

public record CouponRegisteredResult(
    String ownerEmail,
    DiscountInfo discountInfo,
    LocalDateTime expirationAt
) {

    public static CouponRegisteredResult from(
        final String ownerEmail,
        final Coupon coupon
    ) {
        return new CouponRegisteredResult(
            ownerEmail,
            coupon.getDiscountInfo(),
            coupon.getExpirationAt()
        );
    }
}
