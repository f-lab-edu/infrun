package com.flab.infrun.coupon.infrastructure.persistence;

import com.flab.infrun.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

}
