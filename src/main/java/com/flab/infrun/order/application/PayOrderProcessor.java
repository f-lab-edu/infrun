package com.flab.infrun.order.application;

import com.flab.infrun.order.application.command.PayOrderCommand;
import com.flab.infrun.order.application.result.PayedOrderResult;
import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderRepository;
import com.flab.infrun.order.domain.exception.AlreadyCanceledOrderException;
import com.flab.infrun.order.domain.exception.AlreadyCompletedOrderException;
import com.flab.infrun.order.domain.exception.OrderPayAmountNotMatchException;
import com.flab.infrun.payment.domain.Payment;
import com.flab.infrun.payment.domain.PaymentRepository;
import com.flab.infrun.payment.domain.exception.NotAllowedInstallmentMonthException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PayOrderProcessor {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private static final Logger log = LoggerFactory.getLogger(PayOrderProcessor.class);

    @Transactional(dontRollbackOn = {
        AlreadyCanceledOrderException.class,
        AlreadyCompletedOrderException.class,
        OrderPayAmountNotMatchException.class,
        NotAllowedInstallmentMonthException.class})
    public PayedOrderResult execute(final PayOrderCommand command) {
        final Order order = orderRepository.findById(command.orderId());

        try {
            order.pay(command.amount());
            final Payment payment = savePayment(command);

            return PayedOrderResult.from(payment, order.getOrderStatus());
        } catch (AlreadyCanceledOrderException | AlreadyCompletedOrderException
                 | OrderPayAmountNotMatchException | NotAllowedInstallmentMonthException e) {
            log.debug(e.getMessage());
            order.cancel();

            throw e;
        }
    }

    private Payment savePayment(final PayOrderCommand command) {
        final Payment payment = Payment.create(
            command.userId(),
            command.orderId(),
            command.amount(),
            command.payType(),
            command.payMethod(),
            command.installmentMonths());

        return paymentRepository.save(payment);
    }
}
