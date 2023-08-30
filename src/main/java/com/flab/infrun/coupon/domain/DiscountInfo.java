package com.flab.infrun.coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
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

    public static DiscountInfo of(
        final DiscountType discountType,
        final int discountValue
    ) {
        return new DiscountInfo(discountType, discountValue);
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return BigDecimal.valueOf(discountValue);
    }

    public BigDecimal discount(final BigDecimal price) {
        return BigDecimal.valueOf(
            this.discountType.calculateDiscountPrice(price.intValue(), discountValue));
    }
}
