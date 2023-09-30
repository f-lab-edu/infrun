package com.flab.infrun.coupon.application;

import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeCouponRepository implements CouponRepository {

    private final Map<Long, Coupon> persistence = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Coupon save(final Coupon coupon) {
        persistence.put(++sequence, coupon);
        coupon.assignId(sequence);

        return coupon;
    }

    @Override
    public List<Coupon> saveAll(final List<Coupon> coupons) {
        coupons.forEach(coupon -> {
            persistence.put(++sequence, coupon);
            coupon.assignId(sequence);
        });

        return coupons;
    }

    @Override
    public Coupon findByCouponCode(final String couponCode) {
        return persistence.values().stream()
            .filter(coupon -> Objects.equals(coupon.getCode(), couponCode))
            .findFirst()
            .orElseThrow(NotFoundCouponException::new);
    }

    @Override
    public Optional<Coupon> findByCouponCodeWithLock(final String couponCode) {
        return persistence.values().stream()
            .filter(coupon -> Objects.equals(coupon.getCode(), couponCode))
            .findFirst();
    }

    @Override
    public List<Coupon> findAllByOwnerId(final Long ownerId) {
        return persistence.values().stream()
            .filter(coupon -> Objects.equals(coupon.getOwnerId(), ownerId))
            .toList();
    }
}
