package com.flab.infrun.cart.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartFixture {

    private Long id = 1L;
    private Long ownerId = 1L;
    private List<CartItemFixture> cartItems = new ArrayList<>();
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

    public CartFixture cartItems(final CartItemFixture... cartItems) {
        this.cartItems = Arrays.asList(cartItems);
        return this;
    }

    public CartFixture totalPrice(final BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Cart build() {
        final Cart cart = Cart.create(this.ownerId);

        this.cartItems.stream()
            .map(CartItemFixture::build)
            .forEach(cartItem -> cart.addCartItem(cartItem.getLectureId(), cartItem.getPrice()));

        return cart;
    }
}
