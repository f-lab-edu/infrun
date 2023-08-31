package com.flab.infrun.order.application;

import com.flab.infrun.order.application.command.PayOrderCommand;
import com.flab.infrun.order.application.result.PayedOrderResult;
import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderRepository;
import com.flab.infrun.payment.domain.Payment;
import com.flab.infrun.payment.domain.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PayOrderProcessor {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PayedOrderResult execute(final PayOrderCommand command) {
        final Order order = orderRepository.findById(command.orderId());
        final Payment payment = Payment.create(
            command.userId(),
            command.orderId(),
            command.amount(),
            command.payType(),
            command.payMethod());

        order.pay(command.amount());
        paymentRepository.save(payment);

        return PayedOrderResult.from(payment, order.getOrderStatus());
    }
}
