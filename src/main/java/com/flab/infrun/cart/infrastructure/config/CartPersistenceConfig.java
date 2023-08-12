package com.flab.infrun.cart.infrastructure.config;

import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.cart.infrastructure.persistence.CartJpaRepository;
import com.flab.infrun.cart.infrastructure.persistence.CartRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartPersistenceConfig {

    @Bean
    public CartRepository cartRepository(final CartJpaRepository cartJpaRepository) {
        return new CartRepositoryAdapter(cartJpaRepository);
    }
}
