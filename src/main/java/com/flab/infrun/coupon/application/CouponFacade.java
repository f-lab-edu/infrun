package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import com.flab.infrun.coupon.application.result.EnrolledCouponResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponFacade {

    private final CreateCouponProcessor createCouponProcessor;
    private final EnrollCouponProcessor enrollCouponProcessor;

    public CreatedCouponResult createCoupons(
        final CreateCouponCommand command,
        final LocalDateTime currentTime
    ) {
        return createCouponProcessor.execute(command, currentTime);
    }

    public EnrolledCouponResult enrollCoupon(
        final CouponRegisterCommand command,
        final LocalDateTime currentTime
    ) {
        return enrollCouponProcessor.execute(command, currentTime);
    }
}
