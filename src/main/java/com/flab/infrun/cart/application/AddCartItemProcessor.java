package com.flab.infrun.cart.application;

import com.flab.infrun.cart.application.command.AddCartItemCommand;
import com.flab.infrun.cart.application.result.AddedCartItemResult;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartItem;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class AddCartItemProcessor {

    private final CartRepository cartRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public AddedCartItemResult execute(final AddCartItemCommand command) {
        final Lecture lecture = lectureRepository.findById(command.lectureId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));
        final Cart cart = cartRepository.findByOwnerId(command.ownerId())
            .orElse(Cart.create(command.ownerId()));

        cart.addCartItem(CartItem.of(lecture.getId(), BigDecimal.valueOf(lecture.getPrice())));

        return AddedCartItemResult.from(cart.getTotalPrice(), cart.getLectureIds());
    }
}
