package com.flab.infrun.cart.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CartItems {

    @ElementCollection
    @CollectionTable(name = "cart_item", joinColumns = @JoinColumn(name = "cart_id"))
    private Set<CartItem> cartItems = new HashSet<>();

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
