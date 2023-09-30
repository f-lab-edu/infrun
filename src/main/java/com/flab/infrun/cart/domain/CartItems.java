package com.flab.infrun.cart.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class CartItems {

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    List<Long> getLectureIds() {
        return cartItems.stream()
            .map(CartItem::getLectureId)
            .toList();
    }

    void add(final CartItem cartItem) {
        cartItems.add(cartItem);
    }

    boolean delete(final Long lectureId) {
        return cartItems.removeIf(cartItem -> Objects.equals(cartItem.getLectureId(), lectureId));
    }

    BigDecimal calculateTotalPrice() {
        return cartItems.stream()
            .map(CartItem::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean hasCartItem(final List<Long> itemIds) {
        return cartItems.stream()
            .map(CartItem::getLectureId)
            .allMatch(itemIds::contains);
    }
}
