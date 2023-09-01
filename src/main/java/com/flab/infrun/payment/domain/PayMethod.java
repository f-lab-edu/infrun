package com.flab.infrun.payment.domain;

public enum PayMethod {

    CARD("카드"),
    CASH("현금");

    private String description;

    PayMethod(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
