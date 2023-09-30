package com.flab.infrun.cart.infrastructure.persistence;

import com.flab.infrun.cart.domain.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByOwnerId(final Long ownerId);

    @Query("select distinct c from Cart c join fetch c.cartItems.cartItems where c.ownerId = :ownerId")
    Optional<Cart> findWithCartItemsByOwnerId(@Param("ownerId") final Long ownerId);
}
