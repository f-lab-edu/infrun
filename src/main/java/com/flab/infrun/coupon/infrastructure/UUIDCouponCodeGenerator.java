package com.flab.infrun.coupon.infrastructure;

import com.flab.infrun.coupon.domain.CouponCodeGenerator;
import java.util.UUID;


public class UUIDCouponCodeGenerator implements CouponCodeGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
