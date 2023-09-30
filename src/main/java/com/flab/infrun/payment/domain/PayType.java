package com.flab.infrun.payment.domain;

public enum PayType {

    LUMP_SUM("일시불"),
    INSTALLMENT("할부");

    private String description;

    PayType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
