package com.flab.infrun.order.application;

import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.command.CreateOrderCommand;
import com.flab.infrun.order.application.result.CreatedOrderResult;
import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderItem;
import com.flab.infrun.order.domain.OrderRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public CreatedOrderResult execute(
        final CreateOrderCommand command,
        final LocalDateTime currentTime
    ) {
        verifyCart(command.customer().getId(), command.lectureIds());
        final Optional<Coupon> optionalCoupon = verifyAndGetCoupon(
            command.customer(),
            command.couponCode(),
            currentTime);
        final List<Lecture> lectures = lectureRepository.findAllByIdIn(command.lectureIds());
        BigDecimal price = calculatePrice(lectures);
        final List<OrderItem> orderItems = createOrderItems(lectures);

        final Order order = orderRepository.save(
            Order.create(command.customer().getId(), orderItems, price,
                optionalCoupon.orElse(null)));

        return CreatedOrderResult.from(order);
    }

    private Optional<Coupon> verifyAndGetCoupon(
        final Member member,
        final String couponCode,
        final LocalDateTime currentTime
    ) {
        if (!StringUtils.hasText(couponCode)) {
            return Optional.empty();
        }

        final Coupon coupon = couponRepository.findByCouponCode(couponCode);
        coupon.verifyIsUsable(currentTime, member);

        return Optional.of(coupon);
    }

    private void verifyCart(final Long ownerId, final List<Long> lectureIds) {
        cartRepository.findWithCartItemsByOwnerId(ownerId)
            .hasCartItem(lectureIds);
    }

    private BigDecimal calculatePrice(final List<Lecture> lectures) {
        return lectures.stream()
            .map(lecture -> BigDecimal.valueOf(lecture.getPrice()))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    private List<OrderItem> createOrderItems(final List<Lecture> lectures) {
        return lectures.stream()
            .map(lecture -> OrderItem.create(
                lecture.getId(),
                lecture.getMember().getNickname(),
                lecture.getName(),
                BigDecimal.valueOf(lecture.getPrice()),
                BigDecimal.ZERO))
            .toList();
    }
}
