package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.application.result.EnrolledCouponResult;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Component
public class EnrollCouponProcessor {

    private final CouponRepository couponRepository;

    @Transactional
    public EnrolledCouponResult execute(
        final CouponRegisterCommand command,
        final LocalDateTime currentTime
    ) {
        final Coupon coupon = couponRepository.findByCouponCodeWithLock(command.couponCode())
            .orElseThrow(NotFoundCouponException::new);

        coupon.enroll(command.member(), currentTime);

        return EnrolledCouponResult.from(command.member().getEmail(), coupon);
    }
}
