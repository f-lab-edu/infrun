package com.flab.infrun.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import com.flab.infrun.coupon.domain.CouponCodeGenerator;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.CouponValidator;
import com.flab.infrun.coupon.domain.exception.InvalidCouponDiscountAmountException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponDiscountTypeException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponExpirationAtException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponQuantityException;
import com.flab.infrun.coupon.infrastructure.CouponValidatorImpl;
import com.flab.infrun.coupon.infrastructure.UUIDCouponCodeGenerator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class CouponProcessorTest {

    final LocalDateTime expirationAt = LocalDateTime.of(2088, 1, 1, 0, 0, 0);
    final LocalDateTime currentTime = LocalDateTime.of(2023, 6, 30, 0, 0, 0);
    private CouponProcessor couponProcessor;

    @BeforeEach
    void setUp() {
        CouponRepository couponRepository = new StubCouponRepository();
        CouponCodeGenerator couponCodeGenerator = new UUIDCouponCodeGenerator();
        CouponValidator couponValidator = new CouponValidatorImpl();
        couponProcessor = new CouponProcessor(
            couponRepository,
            couponCodeGenerator,
            couponValidator
        );
    }

    @Test
    @DisplayName("쿠폰을 생성한다")
    void createCoupon() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            1000,
            expirationAt,
            3);

        final CreatedCouponResult result = couponProcessor.createCoupons(command, currentTime);

        assertThat(result.couponCodes()).hasSize(3);
        assertThat(result.quantity()).isEqualTo(3);
        assertThat(result.expirationAt()).isEqualTo(expirationAt);
    }

    @Test
    @DisplayName("쿠폰 생성 시 할인 타입이 없으면 예외가 발생한다")
    void createCouponWithoutDiscountType() {
        final CreateCouponCommand command = new CreateCouponCommand(
            null,
            1000,
            expirationAt,
            3);

        assertThatThrownBy(() -> couponProcessor.createCoupons(command, currentTime))
            .isInstanceOf(InvalidCouponDiscountTypeException.class);
    }

    @Test
    @DisplayName("쿠폰 생성 시 할인 금액이 0보다 적으면 예외가 발생한다")
    void createCouponWithDiscountInfo() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            0,
            expirationAt,
            3);

        assertThatThrownBy(() -> couponProcessor.createCoupons(command, currentTime))
            .isInstanceOf(InvalidCouponDiscountAmountException.class);
    }

    @Test
    @DisplayName("쿠폰 생성 시 만료일이 현재 시간보다 이전이면 예외가 발생한다")
    void createCouponWithexpirationAtBefore() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            1000,
            LocalDateTime.of(1995, 6, 30, 0, 0, 0),
            3);

        assertThatThrownBy(() -> couponProcessor.createCoupons(command, currentTime))
            .isInstanceOf(InvalidCouponExpirationAtException.class);
    }

    @Test
    @DisplayName("생성할 쿠폰 개수가 0보다 적으면 예외가 발생한다")
    void createCouponWithQuantityLessThanZero() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            1000,
            expirationAt,
            0);

        assertThatThrownBy(() -> couponProcessor.createCoupons(command, currentTime))
            .isInstanceOf(InvalidCouponQuantityException.class);
    }
}
