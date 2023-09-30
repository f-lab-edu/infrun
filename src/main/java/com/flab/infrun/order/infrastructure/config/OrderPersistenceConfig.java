package com.flab.infrun.order.infrastructure.config;

import com.flab.infrun.order.domain.OrderRepository;
import com.flab.infrun.order.infrastructure.persistence.OrderJpaRepository;
import com.flab.infrun.order.infrastructure.persistence.OrderRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderPersistenceConfig {

    @Bean
    public OrderRepository orderRepository(final OrderJpaRepository orderJpaRepository) {
        return new OrderRepositoryAdapter(orderJpaRepository);
    }
}
