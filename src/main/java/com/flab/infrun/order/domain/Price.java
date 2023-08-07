package com.flab.infrun.order.domain;

import com.flab.infrun.order.domain.exception.InvalidPriceValueException;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Price {

    private BigDecimal totalPrice;
    private BigDecimal discountPrice;

    private Price(final BigDecimal totalPrice, final BigDecimal discountPrice) {
        verifyPrice(totalPrice, discountPrice);
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
    }

    public static Price create(final BigDecimal totalPrice) {
        return new Price(totalPrice, BigDecimal.ZERO);
    }

    public static Price create(final BigDecimal totalPrice, final BigDecimal basePrice) {
        return new Price(totalPrice, basePrice.subtract(totalPrice));
    }

    private void verifyPrice(final BigDecimal totalPrice, final BigDecimal discountPrice) {
        if (totalPrice.compareTo(BigDecimal.ZERO) < 0
            || discountPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceValueException();
        }
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public BigDecimal calculateAmount() {
        return totalPrice.subtract(discountPrice);
    }
}
