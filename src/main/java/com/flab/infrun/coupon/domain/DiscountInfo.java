package com.flab.infrun.coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DiscountInfo {

    private DiscountType discountType;
    private int discountAmount;

    private DiscountInfo(final DiscountType discountType, final int discountAmount) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
    }

    public static DiscountInfo of(final DiscountType discountType, final int discountAmount) {
        return new DiscountInfo(discountType, discountAmount);
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }
}
