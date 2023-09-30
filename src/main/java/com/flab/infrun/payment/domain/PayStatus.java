package com.flab.infrun.payment.domain;

public enum PayStatus {
    PAYMENT_SUCCESS("결제 성공"),
    PAYMENT_CANCEL("결제 취소"),
    PAYMENT_FAIL("결제 실패");

    private final String description;

    PayStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
