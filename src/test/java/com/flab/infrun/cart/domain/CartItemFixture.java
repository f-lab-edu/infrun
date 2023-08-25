package com.flab.infrun.cart.domain;

import static com.flab.infrun.cart.domain.CartFixture.aCartFixture;

import java.math.BigDecimal;

public class CartItemFixture {

    private Long lectureId = 1L;
    private BigDecimal price = BigDecimal.valueOf(30_000);
    private CartFixture cart = aCartFixture();

    public static CartItemFixture aCartItemFixture() {
        return new CartItemFixture();
    }

    public Long getLectureId() {
        return lectureId;
    }

    public BigDecimal getPrice() {
        return price;
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
        return new CartItem(this.cart.build(), this.lectureId, this.price);
    }
}
