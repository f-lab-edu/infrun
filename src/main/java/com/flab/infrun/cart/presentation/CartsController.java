package com.flab.infrun.cart.presentation;

import com.flab.infrun.cart.application.CartsFacade;
import com.flab.infrun.cart.presentation.request.AddCartItemRequest;
import com.flab.infrun.cart.presentation.request.DeleteCartItemRequest;
import com.flab.infrun.cart.presentation.response.AddedCartItemResponse;
import com.flab.infrun.cart.presentation.response.CartsResponse;
import com.flab.infrun.cart.presentation.response.DeletedCartItemResponse;
import com.flab.infrun.common.config.security.CurrentUser;
import com.flab.infrun.common.response.Response;
import com.flab.infrun.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartsController {

    private final CartsFacade facade;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Response getCartItems(@CurrentUser Member member) {
        var result = facade.readCartItems(member.getId());

        return Response.success(CartsResponse.from(result));
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Response addCartItem(
        @CurrentUser Member member,
        @Valid @RequestBody AddCartItemRequest request
    ) {
        var result = facade.addCartItem(request.toCommand(member.getId()));

        return Response.success(AddedCartItemResponse.from(result));
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{lectureId}")
    public Response deleteCartItem(
        @CurrentUser Member member,
        @PathVariable Long lectureId
    ) {
        var result = facade.deleteCartItem(
            new DeleteCartItemRequest(lectureId)
                .toCommand(member.getId()));

        return Response.success(DeletedCartItemResponse.from(result));
    }
}
