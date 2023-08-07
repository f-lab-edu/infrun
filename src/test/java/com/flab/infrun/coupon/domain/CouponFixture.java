package com.flab.infrun.coupon.domain;

import com.flab.infrun.member.domain.MemberFixture;
import java.time.LocalDateTime;

public class CouponFixture {

    private Long id = 1L;
    private String code = "coupon-code";
    private CouponStatus status = CouponStatus.REGISTERED;
    private DiscountInfo discountInfo = DiscountInfo.of(DiscountType.FIX, 1_000);
    private LocalDateTime expirationAt = LocalDateTime.of(2099, 12, 31, 0, 0);
    private MemberFixture ownerFixture = MemberFixture.aMemberFixture();

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

    public CouponFixture ownerFixture(final MemberFixture ownerFixture) {
        this.ownerFixture = ownerFixture;
        return this;
    }

    public Coupon build() {
        final Coupon coupon = Coupon.create(
            code,
            discountInfo,
            expirationAt
        );
        coupon.assignId(id);
        coupon.enroll(ownerFixture.build(), LocalDateTime.now());

        return coupon;
    }
}
