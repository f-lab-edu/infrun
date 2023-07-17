package com.flab.infrun.cart.application;

import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeCartRepository implements CartRepository {

    private final Map<Long, Cart> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Cart save(final Cart cart) {
        persistence.put(++sequence, cart);
        cart.assignId(sequence);
        return cart;
    }

    @Override
    public Optional<Cart> findByOwnerId(final Long ownerId) {
        return persistence.values().stream()
            .filter(cart -> cart.getOwnerId().equals(ownerId))
            .findFirst();
    }
}
