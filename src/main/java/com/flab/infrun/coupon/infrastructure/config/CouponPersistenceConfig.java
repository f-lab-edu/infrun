package com.flab.infrun.coupon.infrastructure.config;

import com.flab.infrun.coupon.infrastructure.persistence.CouponJpaRepository;
import com.flab.infrun.coupon.infrastructure.persistence.CouponRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponPersistenceConfig {

    @Bean
    public CouponRepositoryAdapter couponRepositoryAdapter(
        final CouponJpaRepository couponJpaRepository
    ) {
        return new CouponRepositoryAdapter(couponJpaRepository);
    }
}
