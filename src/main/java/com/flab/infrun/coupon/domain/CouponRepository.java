package com.flab.infrun.coupon.domain;

import java.util.List;

public interface CouponRepository {

    List<Coupon> saveAll(List<Coupon> coupons);
}
