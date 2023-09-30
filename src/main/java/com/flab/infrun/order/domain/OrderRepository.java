package com.flab.infrun.order.domain;

public interface OrderRepository {

    Order save(final Order order);

    Order findById(final Long id);
}
