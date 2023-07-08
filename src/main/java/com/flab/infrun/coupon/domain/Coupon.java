package com.flab.infrun.coupon.domain;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.coupon.domain.exception.AlreadyRegisteredCouponException;
import com.flab.infrun.coupon.domain.exception.ExpiredCouponException;
import com.flab.infrun.member.domain.Member;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
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

    @Version
    private Long version;

    private String code;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Embedded
    private DiscountInfo discountInfo;

    private LocalDateTime expirationAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    @Builder
    private Coupon(final String code, final CouponStatus status, final DiscountInfo discountInfo,
        final LocalDateTime expirationAt, final Member owner) {
        this.code = code;
        this.status = status;
        this.discountInfo = discountInfo;
        this.expirationAt = expirationAt;
        this.owner = owner;
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

    public Member getOwner() {
        return owner;
    }

    public DiscountInfo getDiscountInfo() {
        return discountInfo;
    }

    public LocalDateTime getExpirationAt() {
        return expirationAt;
    }

    public void register(final Member owner, final LocalDateTime currentTime) {
        verifyIsRegistrable(currentTime);
        this.owner = owner;
        this.status = CouponStatus.REGISTERED;
    }

    private void verifyIsRegistrable(final LocalDateTime currentTime) {
        if (this.status != CouponStatus.UNREGISTERED) {
            throw new AlreadyRegisteredCouponException(ErrorCode.ALREADY_REGISTERED_COUPON);
        }
        if (this.expirationAt.isBefore(currentTime)) {
            throw new ExpiredCouponException(ErrorCode.EXPIRED_COUPON);
        }
    }

    // TODO: 쿠폰 사용 로직 작성하기
    public void use() {
        if (this.status == CouponStatus.USED || this.status == CouponStatus.EXPIRED) {
            throw new IllegalArgumentException("이미 사용했거나, 만료된 쿠폰입니다.");
        }
        this.status = CouponStatus.USED;
    }

    @VisibleForTesting
    public void assignId(final Long id) {
        this.id = id;
    }
}
