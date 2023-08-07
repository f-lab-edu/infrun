package com.flab.infrun.order.domain;

import com.flab.infrun.common.entity.BaseEntity;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.domain.exception.InvalidCreateOrderException;
import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @Embedded
    private OrderItems orderItems;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private Order(
        final Member member,
        final List<Lecture> lectures,
        final Price price
    ) {
        verifyOrder(member, lectures);
        this.member = member;
        this.orderItems = OrderItems.create(lectures);
        this.price = price;
        this.orderStatus = OrderStatus.ORDER_CREATED;
    }

    public static Order create(
        final Member member,
        final List<Lecture> lectures,
        final Price price
    ) {
        return new Order(member, lectures, price);
    }

    private void verifyOrder(final Member member, final List<Lecture> lectures) {
        if (Objects.isNull(member) || Objects.isNull(lectures) || lectures.isEmpty()) {
            throw new InvalidCreateOrderException();
        }
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
