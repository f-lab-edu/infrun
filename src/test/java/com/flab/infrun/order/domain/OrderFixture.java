package com.flab.infrun.order.domain;

import com.flab.infrun.coupon.domain.CouponFixture;
import com.flab.infrun.member.domain.MemberFixture;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class OrderFixture {

    private Long id = 1L;
    private MemberFixture memberFixture = MemberFixture.aMemberFixture();
    private Price price = Price.create(BigDecimal.valueOf(100_000));
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

    public OrderFixture memberFixture(final MemberFixture memberFixture) {
        this.memberFixture = memberFixture;
        return this;
    }

    public OrderFixture price(final BigDecimal price) {
        this.price = Price.create(price);
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
            memberFixture.build(),
            buildOrderItems(),
            price,
            couponFixture.build());
        order.assignId(id);

        return order;
    }

    private List<OrderItem> buildOrderItems() {
        return this.orderItemFixtures.stream()
            .map(OrderItemFixture::build)
            .toList();
    }
}
