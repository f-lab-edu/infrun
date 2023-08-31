package com.flab.infrun.payment.domain;

public enum PayType {

    LUMP_SUM("일시불", 1),
    INSTALLMENT("할부", 1);

    private String description;
    private int period;

    PayType(final String description, final int period) {
        this.description = description;
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public int getPeriod() {
        return period;
    }
}
