package com.flab.infrun.cart.application;

import com.flab.infrun.cart.application.command.DeleteCartCommand;
import com.flab.infrun.cart.application.result.DeletedCartResult;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.cart.domain.exception.NotFoundCartException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DeleteCartProcessor {

    private final CartRepository cartRepository;

    @Transactional
    public DeletedCartResult execute(final DeleteCartCommand command) {
        final Cart cart = cartRepository.findByOwnerId(command.ownerId())
            .orElseThrow(() -> new NotFoundCartException());

        cart.deleteCartItem(command.lectureId());

        return DeletedCartResult.from(cart.getTotalPrice(), cart.getLectureIds());
    }
}
