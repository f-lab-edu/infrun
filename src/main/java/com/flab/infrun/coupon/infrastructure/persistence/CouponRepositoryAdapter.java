package com.flab.infrun.coupon.infrastructure.persistence;

import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponRepositoryAdapter implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public List<Coupon> saveAll(final List<Coupon> coupons) {
        return couponJpaRepository.saveAll(coupons);
    }
}
