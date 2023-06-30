package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class StubCouponRepository implements CouponRepository {

    private final Map<Long, Coupon> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public List<Coupon> saveAll(final List<Coupon> coupons) {
        coupons.forEach(coupon -> {
            persistence.put(++sequence, coupon);
            coupon.assignId(sequence);
        });

        return coupons;
    }
}
