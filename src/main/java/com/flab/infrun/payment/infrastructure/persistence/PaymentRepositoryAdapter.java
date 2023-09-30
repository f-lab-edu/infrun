package com.flab.infrun.payment.infrastructure.persistence;

import com.flab.infrun.payment.domain.Payment;
import com.flab.infrun.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(final Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    @Override
    public Payment findByOrderId(final Long orderId) {
        return paymentJpaRepository.findByOrderId(orderId);
    }
}
