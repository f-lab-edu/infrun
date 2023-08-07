package com.flab.infrun.coupon.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DiscountInfo {

    private DiscountType discountType;
    private BigDecimal discountValue;

    private DiscountInfo(final DiscountType discountType, final BigDecimal discountValue) {
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public static DiscountInfo of(final DiscountType discountType,
        final BigDecimal discountAmount) {
        return new DiscountInfo(discountType, discountAmount);
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public BigDecimal discount(final BigDecimal price) {
        return BigDecimal.valueOf(
            this.discountType.calculateDiscountPrice(price.intValue(), discountValue.intValue()));
    }
}
