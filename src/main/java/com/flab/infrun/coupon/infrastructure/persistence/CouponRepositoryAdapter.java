package com.flab.infrun.coupon.infrastructure.persistence;

import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponRepositoryAdapter implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon save(final Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public List<Coupon> saveAll(final List<Coupon> coupons) {
        return couponJpaRepository.saveAll(coupons);
    }

    @Override
    public Coupon findByCouponCode(final String couponCode) {
        return couponJpaRepository.findByCode(couponCode)
            .orElseThrow(NotFoundCouponException::new);
    }

    @Override
    public Optional<Coupon> findByCouponCodeWithLock(final String couponCode) {
        return couponJpaRepository.findByCodeWithLock(couponCode);
    }

    @Override
    public List<Coupon> findAllByOwnerId(final Long ownerId) {
        return couponJpaRepository.findAllByOwnerId(ownerId);
    }
}
