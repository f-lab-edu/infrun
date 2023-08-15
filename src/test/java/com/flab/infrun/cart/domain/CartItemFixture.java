package com.flab.infrun.cart.domain;

import java.math.BigDecimal;

public class CartItemFixture {

    private Long lectureId = 1L;
    private BigDecimal price = BigDecimal.valueOf(30_000);

    public CartItemFixture(final Long lectureId, final BigDecimal price) {
        this.lectureId = lectureId;
        this.price = price;
    }

    public CartItemFixture lectureId(final Long lectureId) {
        this.lectureId = lectureId;
        return this;
    }

    public CartItemFixture price(final BigDecimal price) {
        this.price = price;
        return this;
    }

    public CartItem build() {
        return CartItem.of(this.lectureId, this.price);
    }
}
