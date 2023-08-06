package com.flab.infrun.cart.application;

import com.flab.infrun.cart.application.result.CartsResult;
import com.flab.infrun.cart.application.result.CartsResult.CartItemResult;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CartsReader {

    private final CartRepository cartRepository;
    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public CartsResult read(final Long ownerId) {
        final Optional<Cart> optionalCart = cartRepository.findByOwnerId(ownerId);

        return optionalCart.map(this::mapToCartItemsResult)
            .orElseGet(() -> new CartsResult(List.of(), BigDecimal.ZERO));
    }

    private CartsResult mapToCartItemsResult(final Cart cart) {
        final List<Long> lectureIds = cart.getLectureIds();
        final List<Lecture> lectures = lectureRepository.findAllByIdIn(lectureIds);

        return new CartsResult(lectures.stream()
            .map(lecture -> {
                final Member teacher = memberRepository.findById(lecture.getUserId());
                return new CartItemResult(
                    lecture.getId(),
                    lecture.getName(),
                    teacher.getNickname(),
                    BigDecimal.valueOf(lecture.getPrice())
                );
            })
            .toList(), cart.getTotalPrice());
    }
}
