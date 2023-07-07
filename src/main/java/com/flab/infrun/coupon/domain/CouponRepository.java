package com.flab.infrun.coupon.domain;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {

    Coupon save(final Coupon coupon);

    List<Coupon> saveAll(final List<Coupon> coupons);

    Optional<Coupon> findByCouponCode(final String couponCode);
}
