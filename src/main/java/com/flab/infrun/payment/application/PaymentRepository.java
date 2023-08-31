package com.flab.infrun.payment.application;

import com.flab.infrun.payment.domain.Payment;

public interface PaymentRepository {

    Payment save(final Payment payment);

    Payment findByOrderId(final Long orderId);
}
