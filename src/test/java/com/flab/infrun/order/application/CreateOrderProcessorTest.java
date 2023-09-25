package com.flab.infrun.order.application;

import static com.flab.infrun.cart.domain.CartFixture.aCartFixture;
import static com.flab.infrun.cart.domain.CartItemFixture.aCartItemFixture;
import static com.flab.infrun.coupon.domain.CouponFixture.aCouponFixture;
import static com.flab.infrun.member.domain.MemberFixture.aMemberFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.common.IntegrationTest;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.DiscountInfo;
import com.flab.infrun.coupon.domain.DiscountType;
import com.flab.infrun.coupon.domain.exception.ExpiredCouponException;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import com.flab.infrun.lecture.domain.LectureFixture;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureJpaRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import com.flab.infrun.order.application.command.CreateOrderCommand;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
    @Autowired
    private MemberJpaRepository memberJpaRepository;

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

        final List<Member> all = memberJpaRepository.findAll();
        System.out.println("*************" + all.toString());

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
        final var currentTime = LocalDateTime.of(2023, 12, 31, 0, 0);
        final String couponCode = "coupon-code";
        final var customer = createMember();
        final var command = new CreateOrderCommand(customer, List.of(1L, 2L, 3L), couponCode);
        setupCoupon(currentTime.plusDays(1));

        final var result = sut.execute(command, currentTime);

        assertThat(result).isNotNull();
        assertThat(result.orderId()).isEqualTo(1L);
        assertThat(result.orderStatus()).isEqualTo("주문 생성");
        assertThat(result.totalPrice()).isEqualTo(BigDecimal.valueOf(89_000));
        assertThat(result.orderItems()).hasSize(3);
        assertThat(result.isCouponApplied()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCoupon")
    @DisplayName("쿠폰이 유효하지 않은 경우 예외가 발생한다")
    void createOrder_withInvalidCouponCode(final String couponCode,
        final LocalDateTime expirationAt) {
        final var currentTime = LocalDateTime.of(2023, 12, 31, 0, 0);
        final var customer = createMember();
        final var command = new CreateOrderCommand(customer, List.of(1L, 2L, 3L), couponCode);
        setupCoupon(expirationAt);

        assertThatThrownBy(() -> sut.execute(command, currentTime))
            .isInstanceOfAny(NotFoundCouponException.class, ExpiredCouponException.class);
    }

    private static Stream<Arguments> provideInvalidCoupon() {
        return Stream.of(
            Arguments.of("invalid-code", LocalDateTime.of(2023, 12, 31, 0, 0)),
            Arguments.of("coupon-code", LocalDateTime.of(2023, 12, 30, 0, 0))
        );
    }

    private Member createMember() {
        return aMemberFixture().build();
    }

    private void setupCart() {
        cartRepository.save(
            aCartFixture()
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
                .build()
        );
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

    private void setupCoupon(final LocalDateTime expirationAt) {
        final Coupon coupon = aCouponFixture()
            .expirationAt(expirationAt)
            .discountInfo(
                DiscountInfo.of(DiscountType.FIX, 1_000))
            .buildWithEnrolled();

        couponRepository.save(coupon);
    }
}


