package com.flab.infrun.order.application;

import com.flab.infrun.order.application.command.CreateOrderCommand;
import com.flab.infrun.order.application.command.PayOrderCommand;
import com.flab.infrun.order.application.result.CreatedOrderResult;
import com.flab.infrun.order.application.result.PayedOrderResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderFacade {

    private final CreateOrderProcessor createOrderProcessor;
    private final PayOrderProcessor payOrderProcessor;

    public CreatedOrderResult createOrder(
        final CreateOrderCommand command, final LocalDateTime currentTime
    ) {
        return createOrderProcessor.execute(command, currentTime);
    }

    public PayedOrderResult payOrder(final PayOrderCommand command) {
        return payOrderProcessor.execute(command);
    }
}
