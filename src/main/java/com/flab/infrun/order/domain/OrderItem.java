package com.flab.infrun.order.domain;

import com.flab.infrun.order.domain.exception.InvalidOrderItemPriceException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_items")
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemId;

    private String itemName;

    private BigDecimal basePrice;

    private BigDecimal salesPrice;

    private OrderItem(
        final Long itemId,
        final String itemName,
        final BigDecimal basePrice,
        final BigDecimal salesPrice
    ) {
        verifyOrderItem(basePrice);
        this.itemId = itemId;
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.salesPrice = salesPrice;
    }

    public static OrderItem create(
        final Long itemId,
        final String itemName,
        final BigDecimal basePrice,
        final BigDecimal salesPrice
    ) {
        return new OrderItem(itemId, itemName, basePrice, salesPrice);
    }

    private void verifyOrderItem(final BigDecimal basePrice) {
        if (basePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidOrderItemPriceException();
        }
    }
}
