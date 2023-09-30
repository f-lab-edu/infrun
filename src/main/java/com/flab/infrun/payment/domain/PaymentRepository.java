package com.flab.infrun.payment.domain;

public interface PaymentRepository {

    Payment save(final Payment payment);

    Payment findByOrderId(final Long orderId);
}
