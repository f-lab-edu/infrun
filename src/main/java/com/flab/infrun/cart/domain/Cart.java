package com.flab.infrun.cart.domain;

import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cart_item", joinColumns = @JoinColumn(name = "cart_id"))
    private Set<CartItem> cartItems = new HashSet<>();
    private BigDecimal totalPrice;

    private Cart(final Long ownerId) {
        this.ownerId = ownerId;
    }

    public static Cart create(final Long ownerId) {
        return new Cart(ownerId);
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void addCartItem(final CartItem cartItem) {
        cartItems.add(cartItem);
        totalPrice = calculateTotalPrice();
    }

    public void deleteCartItem(final Long lectureId) {
        cartItems.removeIf(cartItem -> Objects.equals(cartItem.getLectureId(), lectureId));
        totalPrice = calculateTotalPrice();
    }

    private BigDecimal calculateTotalPrice() {
        return cartItems.stream()
            .map(CartItem::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<Long> getLectureIds() {
        return cartItems.stream()
            .map(CartItem::getLectureId)
            .collect(Collectors.toList());
    }

    @VisibleForTesting
    public void assignId(final Long id) {
        this.id = id;
    }
}
