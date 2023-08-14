package com.flab.infrun.order.application;

import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.cart.domain.exception.NotFoundCartException;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.command.CreateOrderCommand;
import com.flab.infrun.order.application.result.CreateOrderResult;
import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderRepository;
import com.flab.infrun.order.domain.Price;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class CreateOrderProcessor {

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final LectureRepository lectureRepository;
    private final CartRepository cartRepository;

    @Transactional
    public CreateOrderResult execute(
        final CreateOrderCommand command,
        final LocalDateTime currentTime
    ) {
        verifyCart(command.customer().getId(), command.lectureIds());
        final Coupon coupon = verifyCoupon(command.customer(), command.couponCode(), currentTime);
        final List<Lecture> lectures = lectureRepository.findAllByIdIn(command.lectureIds());
        final Price price = createPrice(lectures, coupon);

        final Order order = orderRepository.save(
            Order.create(command.customer(), lectures, price));

        return CreateOrderResult.from(order);
    }

    private Coupon verifyCoupon(
        final Member member,
        final String couponCode,
        final LocalDateTime currentTime
    ) {
        if (!StringUtils.hasText(couponCode)) {
            return null;
        }
        final Coupon coupon = couponRepository.findByCouponCode(couponCode);
        coupon.isUsable(currentTime, member);

        return coupon;
    }

    private void verifyCart(final Long ownerId, final List<Long> lectureIds) {
        final Cart cart = cartRepository.findByOwnerId(ownerId)
            .orElseThrow(NotFoundCartException::new);

        cart.hasCartItem(lectureIds);
    }

    private Price createPrice(final List<Lecture> lectures, final Coupon coupon) {
        final BigDecimal totalPrice = calculateTotalPrice(lectures);
        if (Objects.isNull(coupon)) {
            return Price.create(totalPrice);
        }
        final BigDecimal discountValue = coupon.apply(totalPrice);

        return Price.create(totalPrice.subtract(discountValue));
    }

    private BigDecimal calculateTotalPrice(final List<Lecture> lectures) {
        return lectures.stream()
            .map(lecture -> BigDecimal.valueOf(lecture.getPrice()))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }
}
