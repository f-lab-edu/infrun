package com.flab.infrun.coupon.domain;

import com.flab.infrun.coupon.domain.exception.AlreadyRegisteredCouponException;
import com.flab.infrun.coupon.domain.exception.ExpiredCouponException;
import com.flab.infrun.coupon.domain.exception.UnavailableCouponException;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Embedded
    private DiscountInfo discountInfo;

    private LocalDateTime expirationAt;

    private Long ownerId;

    @Builder
    private Coupon(final String code, final CouponStatus status, final DiscountInfo discountInfo,
        final LocalDateTime expirationAt, final Long ownerId) {
        this.code = code;
        this.status = status;
        this.discountInfo = discountInfo;
        this.expirationAt = expirationAt;
        this.ownerId = ownerId;
    }

    public static Coupon create(
        final String code,
        final DiscountInfo discountInfo,
        final LocalDateTime expirationAt
    ) {
        return Coupon.builder()
            .code(code)
            .status(CouponStatus.UNREGISTERED)
            .discountInfo(discountInfo)
            .expirationAt(expirationAt)
            .build();
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public DiscountInfo getDiscountInfo() {
        return discountInfo;
    }

    public LocalDateTime getExpirationAt() {
        return expirationAt;
    }

    public void enroll(final Long ownerId, final LocalDateTime currentTime) {
        verifyIsRegistrable(currentTime);
        this.ownerId = ownerId;
        this.status = CouponStatus.REGISTERED;
    }

    private void verifyIsRegistrable(final LocalDateTime currentTime) {
        if (this.status != CouponStatus.UNREGISTERED) {
            throw new AlreadyRegisteredCouponException();
        }
        if (this.expirationAt.isBefore(currentTime)) {
            throw new ExpiredCouponException();
        }
    }

    // TODO: 쿠폰 사용 로직 작성하기
    public void use() {
        if (verifyIsUsable()) {
            this.status = CouponStatus.USED;
        }
        throw new UnavailableCouponException();
    }

    public boolean verifyIsUsable() {
        return this.status != CouponStatus.USED && this.status != CouponStatus.EXPIRED;
    }

    @VisibleForTesting
    public void assignId(final Long id) {
        this.id = id;
    }
}
