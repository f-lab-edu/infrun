package com.flab.infrun.coupon.domain;

import com.flab.infrun.member.domain.Member;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
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

    public void registerOwner(final Member owner) {
        this.owner = owner;
    }

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
