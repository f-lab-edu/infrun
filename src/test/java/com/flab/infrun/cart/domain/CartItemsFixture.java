package com.flab.infrun.cart.domain;

import java.util.HashSet;
import java.util.Set;

public class CartItemsFixture {

    private Set<CartItem> cartItems = new HashSet<>();

    public static CartItemsFixture aCartItemsFixture() {
        return new CartItemsFixture();
    }

    public CartItemsFixture cartItems(final Set<CartItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public CartItems build() {
        final CartItems items = new CartItems();

        cartItems.stream()
            .forEach(items::add);

        return items;
    }
}
