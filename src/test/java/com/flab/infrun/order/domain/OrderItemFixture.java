package com.flab.infrun.order.domain;

import java.math.BigDecimal;

public class OrderItemFixture {

    private Long id = 1L;
    private Long itemId = 1L;
    private String itemName = "객체지향의 사실과 오해";
    private BigDecimal basePrice = BigDecimal.valueOf(30_000);
    private BigDecimal salesPrice = BigDecimal.ZERO;

    public static OrderItemFixture anOrderItemsFixture() {
        return new OrderItemFixture();
    }

    public OrderItemFixture id(Long id) {
        this.id = id;
        return this;
    }

    public OrderItemFixture itemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public OrderItemFixture itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public OrderItemFixture basePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public OrderItemFixture salesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
        return this;
    }

    public OrderItem build() {
        return OrderItem.create(itemId, itemName, basePrice, salesPrice);
    }
}
