package com.flab.infrun.cart.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CartItem {

    private Long lectureId;

    private BigDecimal price;

    private CartItem(final Long lectureId, final BigDecimal price) {
        this.lectureId = lectureId;
        this.price = price;
    }

    public static CartItem of(final Long lectureId, final BigDecimal price) {
        return new CartItem(lectureId, price);
    }

    public Long getLectureId() {
        return lectureId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CartItem cartItem = (CartItem) o;
        return Objects.equals(lectureId, cartItem.lectureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId);
    }
}
