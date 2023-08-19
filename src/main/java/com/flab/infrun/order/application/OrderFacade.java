package com.flab.infrun.order.application;

import com.flab.infrun.order.application.command.CreateOrderCommand;
import com.flab.infrun.order.application.result.CreatedOrderResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderFacade {

    private final CreateOrderProcessor createOrderProcessor;

    public CreatedOrderResult createOrder(
        final CreateOrderCommand command, final LocalDateTime currentTime
    ) {
        return createOrderProcessor.execute(command, currentTime);
    }
}
