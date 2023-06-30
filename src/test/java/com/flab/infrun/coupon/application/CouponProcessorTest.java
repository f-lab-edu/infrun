package com.flab.infrun.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.domain.CouponCodeGenerator;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.infrastructure.UUIDCouponCodeGenerator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class CouponProcessorTest {

    private CouponProcessor couponProcessor;

    @BeforeEach
    void setUp() {
        CouponRepository couponRepository = new StubCouponRepository();
        CouponCodeGenerator couponCodeGenerator = new UUIDCouponCodeGenerator();
        couponProcessor = new CouponProcessor(couponRepository, couponCodeGenerator);
    }

    @DisplayName("쿠폰을 생성한다")
    @Test
    void createCoupon() {
        final LocalDateTime expirationAt = LocalDateTime.of(2088, 1, 1, 0, 0, 0);
        final LocalDateTime currentTime = LocalDateTime.of(2023, 6, 30, 0, 0, 0);
        final CreateCouponCommand command = new CreateCouponCommand(
            "AMOUNT",
            1000,
            expirationAt,
            3);

        final int result = couponProcessor.createCoupons(command, currentTime);

        assertThat(result).isEqualTo(3);
    }
}
