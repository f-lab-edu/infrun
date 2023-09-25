package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.application.result.CouponView;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import com.flab.infrun.coupon.application.result.EnrolledCouponResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponFacade {

    private final CreateCouponProcessor createCouponProcessor;
    private final EnrollCouponProcessor enrollCouponProcessor;
    private final CouponReader couponReader;

    public List<CouponView> getCoupons(
        final Long ownerId,
        final LocalDateTime currentTime
    ) {
        return couponReader.read(ownerId, currentTime);
    }

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
