package com.flab.infrun.payment.infrastructure.persistence;

import com.flab.infrun.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    Payment findByOrderId(final Long orderId);
}
