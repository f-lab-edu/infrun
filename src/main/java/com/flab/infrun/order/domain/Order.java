package com.flab.infrun.order.domain;

import com.flab.infrun.common.entity.BaseEntity;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.order.domain.exception.AlreadyCanceledOrderException;
import com.flab.infrun.order.domain.exception.AlreadyCompletedOrderException;
import com.flab.infrun.order.domain.exception.InvalidCreateOrderException;
import com.flab.infrun.order.domain.exception.OrderPayAmountNotMatchException;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @Embedded
    private Price price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    private Order(
        final Long customerId,
        final List<OrderItem> orderItems,
        final BigDecimal price,
        final Coupon coupon
    ) {
        verifyOrder(customerId, orderItems);
        this.price = createPrice(price, coupon);
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.orderStatus = OrderStatus.ORDER_CREATED;
        this.coupon = coupon;
    }

    public static Order create(
        final Long customerId,
        final List<OrderItem> orderItems,
        final BigDecimal price,
        final Coupon coupon
    ) {
        return new Order(
            customerId,
            orderItems,
            price,
            coupon);
    }

    private void verifyOrder(final Long customerId, final List<OrderItem> orderItems) {
        if (Objects.isNull(customerId) || customerId <= 0 || Objects.isNull(orderItems)
            || orderItems.isEmpty()) {
            throw new InvalidCreateOrderException();
        }
    }

    private Price createPrice(final BigDecimal price, final Coupon coupon) {
        if (Objects.isNull(coupon)) {
            return Price.create(price);
        }

        return Price.create(coupon.apply(price), price);
    }

    public boolean isCouponApplied() {
        if (Objects.isNull(coupon)) {
            return false;
        }

        return coupon.isApplied();
    }

    public void pay(final BigDecimal payAmount) {
        verifyPay(payAmount);
        this.orderStatus = OrderStatus.ORDER_COMPLETED;
    }

    private void verifyPay(final BigDecimal payAmount) {
        if (this.orderStatus == OrderStatus.ORDER_CANCELED) {
            throw new AlreadyCanceledOrderException();
        }
        if (this.orderStatus == OrderStatus.ORDER_COMPLETED) {
            throw new AlreadyCompletedOrderException();
        }
        if (!this.price.comparePrice(payAmount)) {
            throw new OrderPayAmountNotMatchException();
        }
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return price.getTotalPrice();
    }

    public BigDecimal getDiscountPrice() {
        return price.getDiscountPrice();
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @VisibleForTesting
    public void assignId(final long id) {
        this.id = id;
    }

    @VisibleForTesting
    void assignOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
