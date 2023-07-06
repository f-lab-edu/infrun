package com.flab.infrun.coupon.infrastructure.config;

import com.flab.infrun.coupon.domain.CouponCodeGenerator;
import com.flab.infrun.coupon.infrastructure.UUIDCouponCodeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponCodeGeneratorConfig {

    @Bean
    public CouponCodeGenerator couponCodeGenerator() {
        return new UUIDCouponCodeGenerator();
    }
}
