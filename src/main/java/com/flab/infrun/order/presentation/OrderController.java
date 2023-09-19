package com.flab.infrun.order.presentation;

import com.flab.infrun.common.config.security.CurrentUser;
import com.flab.infrun.common.response.Response;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.OrderFacade;
import com.flab.infrun.order.presentation.request.CreateOrderRequest;
import com.flab.infrun.order.presentation.request.PayOrderRequest;
import com.flab.infrun.order.presentation.response.CreatedOrderResponse;
import com.flab.infrun.order.presentation.response.PayedOrderResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response<CreatedOrderResponse> createOrder(
        @RequestBody final @Valid CreateOrderRequest request,
        @CurrentUser final Member member
    ) {
        final var result = orderFacade.createOrder(request.toCommand(member), LocalDateTime.now());

        return Response.success(CreatedOrderResponse.from(result));
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response<PayedOrderResponse> payOrder(
        @RequestBody @Valid final PayOrderRequest request,
        @CurrentUser final Member member
    ) {
        final var result = orderFacade.payOrder(request.toCommand(member));

        return Response.success(PayedOrderResponse.from(result));
    }
}
