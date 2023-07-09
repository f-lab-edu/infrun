package com.flab.infrun.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.DiscountInfo;
import com.flab.infrun.coupon.domain.DiscountType;
import com.flab.infrun.coupon.domain.exception.AlreadyRegisteredCouponException;
import com.flab.infrun.coupon.domain.exception.ExpiredCouponException;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import com.flab.infrun.member.domain.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class EnrollCouponProcessorTest {

    private final LocalDateTime expirationAt = LocalDateTime.of(2088, 1, 1, 0, 0);
    private final LocalDateTime currentTime = LocalDateTime.of(2023, 6, 30, 0, 0);
    private final String couponCode = "coupon-code";
    private EnrollCouponProcessor sut;

    @BeforeEach
    void setUp() {
        final CouponRepository couponRepository = new StubCouponRepository();
        sut = new EnrollCouponProcessor(couponRepository);

        couponRepository.save(
            Coupon.create(couponCode, DiscountInfo.of(DiscountType.FIX, 1000), expirationAt));
    }

    @Test
    @DisplayName("쿠폰을 등록한다")
    void registerCoupon() {
        final Member member = Member.of("tester", "test@test.com", "1234");
        final CouponRegisterCommand command = new CouponRegisterCommand(member, couponCode);

        final var result = sut.execute(command, currentTime);

        assertThat(result.ownerEmail()).isEqualTo(member.getEmail());
        assertThat(result.discountInfo().getDiscountType()).isEqualTo(DiscountType.FIX);
        assertThat(result.discountInfo().getDiscountValue()).isEqualTo(1000);
        assertThat(result.expirationAt()).isEqualTo(expirationAt);
    }

    @Test
    @DisplayName("쿠폰 등록 시 쿠폰 코드와 일치하는 쿠폰이 없으면 예외가 발생한다")
    void registerCoupon_couponCode_isNotMatched() {
        final Member member = Member.of("tester", "test@test.com", "1234");
        final CouponRegisterCommand command = new CouponRegisterCommand(member, "not-found-code");

        assertThatThrownBy(() -> sut.execute(command, currentTime))
            .isInstanceOf(NotFoundCouponException.class);
    }

    @Test
    @DisplayName("쿠폰 등록 시 현재 시간이 쿠폰 만료일을 넘겼다면 예외가 발생한다")
    void registerCoupon_expirationAt_isBeforeCurrentTime() {
        final Member member = Member.of("tester", "test@test.com", "1234");
        final CouponRegisterCommand command = new CouponRegisterCommand(member, couponCode);

        assertThatThrownBy(() -> sut.execute(command, expirationAt.plusDays(1)))
            .isInstanceOf(ExpiredCouponException.class);
    }

    @Test
    @DisplayName("쿠폰 등록 시 쿠폰이 이미 등록된 상태라면 예외가 발생한다")
    void registerCoupon_couponStatus_isNotUnregistered() {
        final Member member = Member.of("tester", "test@test.com", "1234");
        final CouponRegisterCommand command = new CouponRegisterCommand(member, couponCode);

        assertThatThrownBy(() -> {
            sut.execute(command, currentTime);
            sut.execute(command, currentTime);
        })
            .isInstanceOf(AlreadyRegisteredCouponException.class);
    }
}
