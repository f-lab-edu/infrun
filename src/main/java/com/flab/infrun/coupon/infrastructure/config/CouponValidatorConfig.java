package com.flab.infrun.coupon.infrastructure.config;

import com.flab.infrun.coupon.domain.CouponValidator;
import com.flab.infrun.coupon.infrastructure.CouponValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponValidatorConfig {

    @Bean
    public CouponValidator couponValidator() {
        return new CouponValidatorImpl();
    }
}
