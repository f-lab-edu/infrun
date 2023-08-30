package com.flab.infrun.order.application;

import static com.flab.infrun.cart.domain.CartFixture.aCartFixture;
import static com.flab.infrun.cart.domain.CartItemFixture.aCartItemFixture;
import static com.flab.infrun.coupon.domain.CouponFixture.aCouponFixture;
import static com.flab.infrun.member.domain.MemberFixture.aMemberFixture;
import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.common.IntegrationTest;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.DiscountInfo;
import com.flab.infrun.coupon.domain.DiscountType;
import com.flab.infrun.lecture.domain.LectureFixture;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureJpaRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.order.application.command.CreateOrderCommand;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

final class CreateOrderProcessorTest extends IntegrationTest {

    @Autowired
    private CreateOrderProcessor sut;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private LectureJpaRepository lectureRepository;
    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setup() {
        memberRepository.save(createMember());
        setupCart();
        setupLectures();
    }

    @Test
    @DisplayName("주문을 생성한다")
    void createOrder() {
        final var customer = createMember();
        final var command = new CreateOrderCommand(customer, List.of(1L, 2L, 3L), null);
        final var currentTime = LocalDateTime.of(2023, 12, 31, 0, 0);

        final var result = sut.execute(command, currentTime);

        assertThat(result).isNotNull();
        assertThat(result.orderId()).isEqualTo(1L);
        assertThat(result.orderStatus()).isEqualTo("주문 생성");
        assertThat(result.totalPrice()).isEqualTo(BigDecimal.valueOf(90_000));
        assertThat(result.isCouponApplied()).isFalse();
    }

    @Test
    @DisplayName("쿠폰을 적용하여 주문을 생성한다")
    void createOrder_withCoupon() {
        setupCoupon();
        final var customer = createMember();
        final var command = new CreateOrderCommand(customer, List.of(1L, 2L, 3L), "coupon-code");
        final var currentTime = LocalDateTime.of(2023, 12, 31, 0, 0);

        final var result = sut.execute(command, currentTime);

        assertThat(result).isNotNull();
        assertThat(result.orderId()).isEqualTo(1L);
        assertThat(result.orderStatus()).isEqualTo("주문 생성");
        assertThat(result.totalPrice()).isEqualTo(BigDecimal.valueOf(89_000));
        assertThat(result.orderItems()).hasSize(3);
        assertThat(result.isCouponApplied()).isTrue();
    }

    private void setupLectures() {
        lectureRepository.saveAll(
            List.of(
                LectureFixture.aLectureFixture()
                    .id(1L)
                    .price(30_000)
                    .build(),
                LectureFixture.aLectureFixture()
                    .id(2L)
                    .price(20_000)
                    .build(),
                LectureFixture.aLectureFixture()
                    .id(3L)
                    .price(40_000)
                    .build()
            )
        );
    }

    private void setupCart() {
        final Cart build = aCartFixture()
            .cartItems(
                aCartItemFixture()
                    .lectureId(1L)
                    .price(BigDecimal.valueOf(30_000)),
                aCartItemFixture()
                    .lectureId(2L)
                    .price(BigDecimal.valueOf(20_000)),
                aCartItemFixture()
                    .lectureId(3L)
                    .price(BigDecimal.valueOf(40_000))
            )
            .build();
        cartRepository.save(
            build
        );
    }

    private Member createMember() {
        return aMemberFixture().build();
    }

    private void setupCoupon() {
        couponRepository.save(
            aCouponFixture()
                .discountInfo(
                    DiscountInfo.of(DiscountType.FIX, 1_000))
                .build());
    }
}


