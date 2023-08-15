package com.flab.infrun.cart.domain;

import java.math.BigDecimal;

public class CartFixture {

    private Long id = 1L;
    private Long ownerId = 1L;
    private CartItems cartItems = CartItemsFixture.aCartItemsFixture().build();
    private BigDecimal totalPrice = BigDecimal.valueOf(100_000);


    public static CartFixture aCartFixture() {
        return new CartFixture();
    }

    public CartFixture id(final Long id) {
        this.id = id;
        return this;
    }

    public CartFixture ownerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public CartFixture cartItems(final CartItems cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public CartFixture totalPrice(final BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Cart build() {
        final Cart cart = Cart.create(this.ownerId);
        cart.assignId(this.id);

        return cart;
    }
}
