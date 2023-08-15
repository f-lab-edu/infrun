package com.flab.infrun.coupon.application.command;

import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.DiscountInfo;
import com.flab.infrun.coupon.domain.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCouponCommand(
    String discountType,
    int discountAmount,
    LocalDateTime expirationAt,
    int quantity
) {

    public Coupon toEntity(final String code) {
        return Coupon.create(
            code,
            DiscountInfo.of(
                DiscountType.valueOf(discountType),
                BigDecimal.valueOf(discountAmount)
            ),
            expirationAt
        );
    }
}
