package com.flab.infrun.order.domain;

public enum OrderStatus {

    ORDER_CREATED("주문 생성"),
    ORDER_COMPLETED("주문 완료"),
    ORDER_CANCELED("주문 취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
