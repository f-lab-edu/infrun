package com.flab.infrun.order.application;

import static com.flab.infrun.coupon.domain.CouponFixture.aCouponFixture;
import static com.flab.infrun.order.domain.OrderFixture.anOrderFixture;
import static com.flab.infrun.order.domain.OrderItemFixture.anOrderItemsFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.common.IntegrationTest;
import com.flab.infrun.coupon.domain.CouponFixture;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.CouponStatus;
import com.flab.infrun.coupon.domain.DiscountInfo;
import com.flab.infrun.coupon.domain.DiscountType;
import com.flab.infrun.member.domain.MemberFixture;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.order.application.command.PayOrderCommand;
import com.flab.infrun.order.domain.Order;
import com.flab.infrun.order.domain.OrderFixture;
import com.flab.infrun.order.domain.OrderRepository;
import com.flab.infrun.order.domain.OrderStatus;
import com.flab.infrun.order.domain.exception.AlreadyCanceledOrderException;
import com.flab.infrun.order.domain.exception.AlreadyCompletedOrderException;
import com.flab.infrun.order.domain.exception.OrderPayAmountNotMatchException;
import com.flab.infrun.payment.domain.PayMethod;
import com.flab.infrun.payment.domain.PayStatus;
import com.flab.infrun.payment.domain.PayType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

final class PayOrderProcessorTest extends IntegrationTest {

    @Autowired
    private PayOrderProcessor sut;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setup() {
        memberRepository.save(MemberFixture.aMemberFixture().build());
    }

    @Test
    @DisplayName("주문을 결제하면 결제 정보가 저장되고 주문의 상태가 주문 완료로 변경된다")
    void payOrder_createPayment() {
        setupCouponAndOrder(OrderStatus.ORDER_CREATED);
        final var command = new PayOrderCommand(
            1L,
            1L,
            BigDecimal.valueOf(40_000),
            PayMethod.CARD,
            PayType.LUMP_SUM);

        final var result = sut.execute(command);

        assertThat(result.amount()).isEqualTo(BigDecimal.valueOf(40_000));
        assertThat(result.payMethod()).isEqualTo(PayMethod.CARD);
        assertThat(result.payType()).isEqualTo(PayType.LUMP_SUM);
        assertThat(result.payedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(result.orderStatus()).isEqualTo(OrderStatus.ORDER_COMPLETED.getDescription());
        assertThat(result.payStatus()).isEqualTo(PayStatus.PAYMENT_SUCCESS.getDescription());
    }

    @Test
    @DisplayName("주문 금액과 결제 금액이 일치하지 않는 경우 예외가 발생하고 주문이 취소된다")
    void payOrder_withIncorrectPayAmount() {
        setupCouponAndOrder(OrderStatus.ORDER_CREATED);
        final var command = new PayOrderCommand(
            1L,
            1L,
            BigDecimal.ZERO,
            PayMethod.CARD,
            PayType.LUMP_SUM);

        assertThatThrownBy(() -> sut.execute(command))
            .isInstanceOf(OrderPayAmountNotMatchException.class);

        final Order order = orderRepository.findById(1L);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCELED);
        assertThat(order.getCouponStatus()).isEqualTo(CouponStatus.REGISTERED);
    }

    @ParameterizedTest
    @MethodSource("provideOrderStatus")
    @DisplayName("이미 취소됐거나, 완료된 주문을 결제하면 예외가 발생하고 주문이 취소된다")
    void payOrder_alreadyCompleted(final OrderStatus orderStatus) {
        setupCouponAndOrder(orderStatus);
        final var command = new PayOrderCommand(
            1L,
            1L,
            BigDecimal.valueOf(40_000),
            PayMethod.CARD,
            PayType.LUMP_SUM);

        assertThatThrownBy(() -> sut.execute(command))
            .isInstanceOfAny(
                AlreadyCanceledOrderException.class,
                AlreadyCompletedOrderException.class);

        final Order order = orderRepository.findById(1L);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCELED);
        assertThat(order.getCouponStatus()).isEqualTo(CouponStatus.REGISTERED);
    }

    private static Stream<Arguments> provideOrderStatus() {
        return Stream.of(
            Arguments.of(OrderStatus.ORDER_CANCELED),
            Arguments.of(OrderStatus.ORDER_COMPLETED)
        );
    }

    private void setupCouponAndOrder(final OrderStatus orderStatus) {
        final CouponFixture couponFixture = aCouponFixture()
            .discountInfo(
                DiscountInfo.of(
                    DiscountType.FIX,
                    10_000
                )
            );
        final OrderFixture orderFixture = anOrderFixture()
            .orderStatus(orderStatus)
            .orderItems(
                anOrderItemsFixture()
                    .id(1L)
                    .itemId(1L)
                    .basePrice(BigDecimal.valueOf(30_000)),
                anOrderItemsFixture()
                    .id(2L)
                    .itemId(2L)
                    .basePrice(BigDecimal.valueOf(20_000))
            )
            .price(BigDecimal.valueOf(50_000))
            .couponFixture(couponFixture);

        couponRepository.save(couponFixture.build());
        orderRepository.save(orderFixture.build());
    }
}