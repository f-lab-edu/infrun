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
import com.flab.infrun.order.domain.Price;
import groovy.util.logging.Slf4j;
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
@Slf4j
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
        final Coupon coupon = verifyCoupon(command.customer(), command.couponCode(), currentTime);
        final List<Lecture> lectures = lectureRepository.findAllByIdIn(command.lectureIds());
        final Price price = createPrice(lectures, coupon);
        final List<OrderItem> orderItems = createOrderItems(lectures);

        final Order order = orderRepository.save(
            Order.create(command.customer(), orderItems, price, coupon));

        return CreatedOrderResult.from(order);
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
        cartRepository.findWithCartItemsByOwnerId(ownerId)
            .hasCartItem(lectureIds);
    }

    private Price createPrice(final List<Lecture> lectures, final Coupon coupon) {
        final BigDecimal basePrice = calculateBasePrice(lectures);
        if (Objects.isNull(coupon)) {
            return Price.create(basePrice);
        }
        final BigDecimal totalPrice = coupon.apply(basePrice);

        return Price.create(totalPrice, basePrice);
    }

    private BigDecimal calculateBasePrice(final List<Lecture> lectures) {
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
