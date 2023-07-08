package com.flab.infrun.coupon.application;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.application.result.CouponRegisteredResult;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Component
public class RegisterCouponProcessor {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponRegisteredResult execute(
        final CouponRegisterCommand command,
        final LocalDateTime currentTime
    ) {
        final Coupon coupon = couponRepository.findByCouponCodeWithLock(command.couponCode())
            .orElseThrow(() -> new NotFoundCouponException(ErrorCode.NOT_FOUND_COUPON));

        coupon.register(command.member(), currentTime);

        return CouponRegisteredResult.from(command.member().getEmail(), coupon);
    }
}
