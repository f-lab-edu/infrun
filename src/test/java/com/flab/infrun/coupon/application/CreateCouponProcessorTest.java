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

final class CreateCouponProcessorTest {

    private final LocalDateTime expirationAt = LocalDateTime.of(2088, 1, 1, 0, 0, 0);
    private final LocalDateTime currentTime = LocalDateTime.of(2023, 6, 30, 0, 0, 0);
    private CreateCouponProcessor sut;

    @BeforeEach
    void setUp() {
        final CouponRepository couponRepository = new FakeCouponRepository();
        CouponCodeGenerator couponCodeGenerator = new UUIDCouponCodeGenerator();
        CouponValidator couponValidator = new CouponValidatorImpl();
        sut = new CreateCouponProcessor(
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

        final CreatedCouponResult result = sut.execute(command, currentTime);

        assertThat(result.couponCodes()).hasSize(3);
        assertThat(result.quantity()).isEqualTo(3);
        assertThat(result.expirationAt()).isEqualTo(expirationAt);
    }

    @Test
    @DisplayName("쿠폰 생성 시 할인 타입이 없으면 예외가 발생한다")
    void createCoupon_without_discountType() {
        final CreateCouponCommand command = new CreateCouponCommand(
            null,
            1000,
            expirationAt,
            3);

        assertThatThrownBy(() -> sut.execute(command, currentTime))
            .isInstanceOf(InvalidCouponDiscountTypeException.class);
    }

    @Test
    @DisplayName("쿠폰 생성 시 할인 금액이 0보다 적으면 예외가 발생한다")
    void createCoupon_withDiscountAmount_lessThanZero() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            0,
            expirationAt,
            3);

        assertThatThrownBy(() -> sut.execute(command, currentTime))
            .isInstanceOf(InvalidCouponDiscountAmountException.class);
    }

    @Test
    @DisplayName("쿠폰 생성 시 만료일이 현재 시간보다 이전이면 예외가 발생한다")
    void createCoupon_withExpirationAt_beforeThanNow() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            1000,
            LocalDateTime.of(1995, 6, 30, 0, 0, 0),
            3);

        assertThatThrownBy(() -> sut.execute(command, currentTime))
            .isInstanceOf(InvalidCouponExpirationAtException.class);
    }

    @Test
    @DisplayName("생성할 쿠폰 개수가 0보다 적으면 예외가 발생한다")
    void createCoupon_withQuantity_LessThanZero() {
        final CreateCouponCommand command = new CreateCouponCommand(
            "FIX",
            1000,
            expirationAt,
            0);

        assertThatThrownBy(() -> sut.execute(command, currentTime))
            .isInstanceOf(InvalidCouponQuantityException.class);
    }
}
