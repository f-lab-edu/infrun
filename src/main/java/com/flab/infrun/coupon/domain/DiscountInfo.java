package com.flab.infrun.coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DiscountInfo {

    private DiscountType discountType;
    private int discountValue;

    private DiscountInfo(final DiscountType discountType, final int discountValue) {
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public static DiscountInfo of(final DiscountType discountType, final int discountAmount) {
        return new DiscountInfo(discountType, discountAmount);
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int getDiscountValue() {
        return discountValue;
    }
}
