package com.flab.infrun.cart.infrastructure.persistence;

import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    @Override
    public Cart save(final Cart cart) {
        return cartJpaRepository.save(cart);
    }

    @Override
    public Optional<Cart> findByOwnerId(final Long ownerId) {
        return cartJpaRepository.findByOwnerId(ownerId);
    }
}
