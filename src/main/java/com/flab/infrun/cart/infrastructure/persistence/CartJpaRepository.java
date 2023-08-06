package com.flab.infrun.cart.infrastructure.persistence;

import com.flab.infrun.cart.domain.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByOwnerId(final Long ownerId);
}
