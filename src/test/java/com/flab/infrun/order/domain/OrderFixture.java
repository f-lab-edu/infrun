package com.flab.infrun.order.domain;

import com.flab.infrun.coupon.domain.CouponFixture;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class OrderFixture {

    private Long id = 1L;
    private Long customerId = 1L;
    private BigDecimal price = BigDecimal.valueOf(100_000);
    private List<OrderItemFixture> orderItemFixtures = List.of(
        OrderItemFixture.anOrderItemsFixture());
    private OrderStatus orderStatus = OrderStatus.ORDER_CREATED;
    private CouponFixture couponFixture = CouponFixture.aCouponFixture();

    public static OrderFixture anOrderFixture() {
        return new OrderFixture();
    }

    public OrderFixture id(final Long id) {
        this.id = id;
        return this;
    }

    public OrderFixture customerId(final Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderFixture price(final BigDecimal price) {
        this.price = price;
        return this;
    }

    public OrderFixture orderItems(final OrderItemFixture... orderItemFixtures) {
        this.orderItemFixtures = Arrays.asList(orderItemFixtures);
        return this;
    }

    public OrderFixture orderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderFixture couponFixture(final CouponFixture couponFixture) {
        this.couponFixture = couponFixture;
        return this;
    }

    public Order build() {
        final Order order = Order.create(
            customerId,
            buildOrderItems(),
            price,
            couponFixture.build());
        order.assignId(id);
        order.assignOrderStatus(orderStatus);

        return order;
    }

    private List<OrderItem> buildOrderItems() {
        return this.orderItemFixtures.stream()
            .map(OrderItemFixture::build)
            .toList();
    }
}
