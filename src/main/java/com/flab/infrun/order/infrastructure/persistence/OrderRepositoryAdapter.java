package com.flab.infrun.order.infrastructure.persistence;

import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderRepository;
import com.flab.infrun.order.domain.exception.NotFoundOrderException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(final Order order) {
        return orderJpaRepository.save(order);
    }

    @Override
    public Order findById(final Long id) {
        return orderJpaRepository.findById(id)
            .orElseThrow(NotFoundOrderException::new);
    }
}
