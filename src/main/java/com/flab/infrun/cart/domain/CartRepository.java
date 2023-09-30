package com.flab.infrun.cart.domain;

import java.util.Optional;

public interface CartRepository {

    Cart save(final Cart cart);

    Optional<Cart> findByOwnerId(final Long ownerId);

    Cart findWithCartItemsByOwnerId(final Long ownerId);
}
