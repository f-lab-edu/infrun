package com.flab.infrun.cart.presentation;

import com.flab.infrun.cart.application.CartFacade;
import com.flab.infrun.cart.presentation.request.AddCartItemRequest;
import com.flab.infrun.cart.presentation.request.DeleteCartItemRequest;
import com.flab.infrun.common.config.security.CurrentUser;
import com.flab.infrun.common.response.Response;
import com.flab.infrun.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartFacade facade;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public Response addCartItem(
        @CurrentUser Member member,
        @Valid AddCartItemRequest request
    ) {
        var result = facade.addCartItem(request.toCommand(member.getId()));

        return Response.success(result);
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

        return Response.success(result);
    }
}
