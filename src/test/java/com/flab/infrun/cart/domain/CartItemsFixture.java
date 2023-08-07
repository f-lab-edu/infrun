package com.flab.infrun.cart.domain;

import static com.flab.infrun.cart.domain.CartItemFixture.aCartItemFixture;

import java.util.Arrays;
import java.util.List;

public class CartItemsFixture {

    private List<CartItemFixture> cartItems = List.of(aCartItemFixture());

    public static CartItemsFixture aCartItemsFixture() {
        return new CartItemsFixture();
    }

    public CartItemsFixture cartItems(final CartItemFixture... cartItems) {
        this.cartItems = Arrays.asList(cartItems);
        return this;
    }

    public List<CartItem> build() {
        return cartItems.stream()
            .map(CartItemFixture::build)
            .toList();
    }
}
