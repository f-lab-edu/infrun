package com.flab.infrun.payment.domain;

import com.flab.infrun.common.entity.BaseEntity;
import com.flab.infrun.payment.domain.exception.NotAllowedInstallmentMonthException;
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
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PayType payType;
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    private Integer installmentMonth;

    private Payment(
        final Long userId,
        final Long orderId,
        final BigDecimal amount,
        final PayType payType,
        final PayMethod payMethod,
        final Integer installmentMonth
    ) {
        verifyInstallment(payType, installmentMonth);
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.payType = payType;
        this.payMethod = payMethod;
        this.payStatus = PayStatus.PAYMENT_SUCCESS;
        this.installmentMonth = installmentMonth;
    }

    private void verifyInstallment(final PayType payType, final Integer installmentMonth) {
        if (payType == PayType.INSTALLMENT && installmentMonth < 1) {
            throw new NotAllowedInstallmentMonthException();
        }
    }

    public static Payment create(
        final Long userId,
        final Long orderId,
        final BigDecimal amount,
        final PayType payType,
        final PayMethod payMethod,
        final Integer installmentMonth
    ) {
        return new Payment(userId, orderId, amount, payType, payMethod, installmentMonth);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PayType getPayType() {
        return payType;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public Integer getInstallmentMonth() {
        return installmentMonth;
    }
}
