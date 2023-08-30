package com.flab.infrun.payment.domain;

public enum PayType {

    CARD("카드"),
    CASH("현금");

    private String description;

    PayType(final String description) {
    }

    public String getDescription() {
        return description;
    }
}
