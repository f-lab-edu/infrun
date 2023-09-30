package com.flab.infrun.coupon.domain;

import java.time.LocalDateTime;

public class CouponFixture {

    private Long id = 1L;
    private String code = "coupon-code";
    private CouponStatus status = CouponStatus.REGISTERED;
    private DiscountInfo discountInfo = DiscountInfo.of(DiscountType.FIX, 1_000);
    private LocalDateTime expirationAt = LocalDateTime.of(2099, 12, 31, 0, 0);
    private Long ownerId = 1L;

    public static CouponFixture aCouponFixture() {
        return new CouponFixture();
    }

    public CouponFixture id(final Long id) {
        this.id = id;
        return this;
    }

    public CouponFixture code(final String code) {
        this.code = code;
        return this;
    }

    public CouponFixture status(final CouponStatus status) {
        this.status = status;
        return this;
    }

    public CouponFixture discountInfo(final DiscountInfo discountInfo) {
        this.discountInfo = discountInfo;
        return this;
    }

    public CouponFixture expirationAt(final LocalDateTime expirationAt) {
        this.expirationAt = expirationAt;
        return this;
    }

    public CouponFixture ownerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public Coupon build() {
        final Coupon coupon = Coupon.create(
            code,
            discountInfo,
            expirationAt
        );

        coupon.assignId(id);

        return coupon;
    }

    public Coupon buildWithEnrolled() {
        final Coupon coupon = Coupon.create(
            code,
            discountInfo,
            expirationAt
        );

        coupon.assignId(id);
        coupon.enroll(ownerId, LocalDateTime.now());

        return coupon;
    }
}
