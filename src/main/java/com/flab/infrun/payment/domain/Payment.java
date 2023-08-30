package com.flab.infrun.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private String payName;
    private String paymentKey;
    @Enumerated(EnumType.STRING)
    private PayType payType;
    private boolean isSuccess;
    private boolean isCanceled;

    private Payment(
        final Long userId,
        final Long orderId,
        final BigDecimal amount,
        final String payName,
        final String paymentKey,
        final PayType payType
    ) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.payName = payName;
        this.paymentKey = paymentKey;
        this.payType = payType;
        this.isSuccess = false;
        this.isCanceled = false;
    }

    public static Payment of(
        final Long userId,
        final Long orderId,
        final BigDecimal amount,
        final String payName,
        final String paymentKey,
        final PayType payType
    ) {
        return new Payment(userId, orderId, amount, payName, paymentKey, payType);
    }
}
