package com.flab.infrun.coupon.domain;

import com.flab.infrun.coupon.domain.exception.AlreadyRegisteredCouponException;
import com.flab.infrun.coupon.domain.exception.AlreadyUsedCouponException;
import com.flab.infrun.coupon.domain.exception.ExpiredCouponException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponOwnerException;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupons")
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

    public void enroll(final Long ownerId, final LocalDateTime currentTime) {
        verifyIsRegistrableAndExpireIfNecessary(currentTime);
        this.ownerId = ownerId;
        this.status = CouponStatus.REGISTERED;
    }

    private void verifyIsRegistrableAndExpireIfNecessary(final LocalDateTime currentTime) {
        if (this.status == CouponStatus.REGISTERED) {
            throw new AlreadyRegisteredCouponException();
        }
        if (this.status == CouponStatus.USED) {
            throw new AlreadyUsedCouponException();
        }
        if (this.status == CouponStatus.EXPIRED) {
            throw new ExpiredCouponException();
        }
        if (this.expirationAt.isBefore(currentTime)) {
            this.status = CouponStatus.EXPIRED;
            throw new ExpiredCouponException();
        }
    }

    public BigDecimal apply(final BigDecimal price) {
        this.status = CouponStatus.USED;
        return this.discountInfo.discount(price);
    }

    public void verifyIsUsableAndExpireIfNecessary(
        final LocalDateTime currentTime,
        final Long customerId
    ) {
        if (!this.ownerId.equals(customerId)) {
            throw new InvalidCouponOwnerException();
        }
        if (this.status == CouponStatus.EXPIRED) {
            throw new ExpiredCouponException();
        }
        if (this.expirationAt.isBefore(currentTime)) {
            this.status = CouponStatus.EXPIRED;
            throw new ExpiredCouponException();
        }
        if (this.status == CouponStatus.USED) {
            throw new AlreadyUsedCouponException();
        }
    }

    public void unapply() {
        this.status = CouponStatus.REGISTERED;
    }

    public boolean isApplied() {
        return this.status == CouponStatus.USED;
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

    @VisibleForTesting
    public void assignId(final Long id) {
        this.id = id;
    }
}
