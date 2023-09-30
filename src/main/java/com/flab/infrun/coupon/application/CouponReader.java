package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.application.result.CouponView;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CouponReader {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public List<CouponView> read(final Long ownerId, final LocalDateTime currentTime) {
        final List<Coupon> coupons = couponRepository.findAllByOwnerId(ownerId);

        return mapToCouponView(currentTime, coupons);
    }

    private List<CouponView> mapToCouponView(
        final LocalDateTime currentTime,
        final List<Coupon> coupons
    ) {
        return coupons.stream()
            .filter(coupon -> coupon.getExpirationAt().isBefore(currentTime))
            .map(coupon -> new CouponView(
                Duration.between(coupon.getExpirationAt(), currentTime).toDays(),
                coupon.getDiscountInfo().getDiscountValue(),
                coupon.getDiscountInfo().getDiscountType().name()
            ))
            .toList();
    }
}
