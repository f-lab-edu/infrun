package com.flab.infrun.cart.application;

import com.flab.infrun.cart.application.command.AddCartItemCommand;
import com.flab.infrun.cart.application.command.DeleteCartItemCommand;
import com.flab.infrun.cart.application.result.AddedCartItemResult;
import com.flab.infrun.cart.application.result.CartsResult;
import com.flab.infrun.cart.application.result.DeletedCartItemResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartsFacade {

    private final AddCartItemProcessor addCartItemProcessor;
    private final DeleteCartItemProcessor deleteCartItemProcessor;
    private final CartsReader cartsReader;

    public CartsResult readCartItems(final Long memberId) {
        return cartsReader.read(memberId);
    }

    public AddedCartItemResult addCartItem(final AddCartItemCommand command) {
        return addCartItemProcessor.execute(command);
    }

    public DeletedCartItemResult deleteCartItem(final DeleteCartItemCommand command) {
        return deleteCartItemProcessor.execute(command);
    }
}
