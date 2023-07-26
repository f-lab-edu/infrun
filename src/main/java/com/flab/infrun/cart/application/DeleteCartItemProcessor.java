package com.flab.infrun.cart.application;

import com.flab.infrun.cart.application.command.DeleteCartItemCommand;
import com.flab.infrun.cart.application.result.DeletedCartItemResult;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.cart.domain.exception.NotFoundCartException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DeleteCartItemProcessor {

    private final CartRepository cartRepository;

    @Transactional
    public DeletedCartItemResult execute(final DeleteCartItemCommand command) {
        final Cart cart = cartRepository.findByOwnerId(command.ownerId())
            .orElseThrow(() -> new NotFoundCartException());

        cart.deleteCartItem(command.lectureId());

        return DeletedCartItemResult.from(cart.getLectureIds());
    }
}
