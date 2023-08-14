package com.flab.infrun.order.infrastructure.persistence;

import com.flab.infrun.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
