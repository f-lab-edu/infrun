package com.flab.infrun.order.domain;

public enum OrderStatus {

    ORDER_CREATED("주문 생성"),
    ORDER_PAYED("주문 결제"),
    ORDER_COMPLETED("주문 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
