package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponCodeGenerator;
import com.flab.infrun.coupon.domain.CouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class CouponProcessor {

    private final CouponRepository couponRepository;
    private final CouponCodeGenerator couponCodeGenerator;

    public CouponProcessor(
        final CouponRepository couponRepository,
        final CouponCodeGenerator couponCodeGenerator
    ) {
        this.couponRepository = couponRepository;
        this.couponCodeGenerator = couponCodeGenerator;
    }

    public int createCoupons(
        final CreateCouponCommand command,
        final LocalDateTime currentTime
    ) {
        final List<Coupon> coupons = generateCoupons(command);
        final List<Coupon> savedCoupons = couponRepository.saveAll(coupons);

        return savedCoupons.size();
    }

    private List<Coupon> generateCoupons(final CreateCouponCommand command) {
        final int quantity = command.quantity();

        return IntStream.range(0, quantity)
            .mapToObj(i -> command.toEntity(couponCodeGenerator.generate()))
            .toList();
    }
}
