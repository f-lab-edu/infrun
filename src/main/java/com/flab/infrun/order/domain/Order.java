package com.flab.infrun.order.domain;

import com.flab.infrun.common.entity.BaseEntity;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.domain.exception.InvalidCreateOrderException;
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
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Embedded
    private Price price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    private Order(
        final Member member,
        final List<OrderItem> orderItems,
        final Price price,
        final Coupon coupon
    ) {
        verifyOrder(member, orderItems);
        this.member = member;
        this.orderItems = orderItems;
        this.price = price;
        this.orderStatus = OrderStatus.ORDER_CREATED;
        this.coupon = coupon;
    }

    public static Order create(
        final Member member,
        final List<OrderItem> orderItems,
        final Price price,
        final Coupon coupon
    ) {
        return new Order(member, orderItems, price, coupon);
    }

    private void verifyOrder(final Member member, final List<OrderItem> orderItems) {
        if (Objects.isNull(member) || Objects.isNull(orderItems) || orderItems.isEmpty()) {
            throw new InvalidCreateOrderException();
        }
    }

    public boolean isCouponApplied() {
        return Objects.nonNull(coupon);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return price.getTotalPrice();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @VisibleForTesting
    public void assignId(final long id) {
        this.id = id;
    }
}
