package com.flab.infrun.cart.domain;

import com.flab.infrun.cart.domain.exception.NotFoundCartItemException;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "carts")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    @Embedded
    private CartItems cartItems = new CartItems();
    private BigDecimal totalPrice;

    private Cart(final Long ownerId) {
        this.ownerId = ownerId;
    }

    public static Cart create(final Long ownerId) {
        return new Cart(ownerId);
    }

    private void calculateTotalPrice() {
        totalPrice = cartItems.calculateTotalPrice();
    }

    public void addCartItem(final Long lectureId, final BigDecimal price) {
        cartItems.add(new CartItem(this, lectureId, price));
        calculateTotalPrice();
    }

    public void deleteCartItem(final Long lectureId) {
        if (!cartItems.delete(lectureId)) {
            throw new NotFoundCartItemException();
        }
        calculateTotalPrice();
    }

    public void hasCartItem(final List<Long> itemIds) {
        if (!cartItems.hasCartItem(itemIds)) {
            throw new NotFoundCartItemException();
        }
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<Long> getLectureIds() {
        return cartItems.getLectureIds();
    }

    @VisibleForTesting
    public void assignId(final Long id) {
        this.id = id;
    }
}
