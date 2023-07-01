package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponFacade {

    private final CouponProcessor couponProcessor;

    public CreatedCouponResult createCoupons(
        final CreateCouponCommand command,
        final LocalDateTime currentTime
    ) {
        return couponProcessor.createCoupons(command, currentTime);
    }
}
