package com.flab.infrun.coupon.domain;

import java.time.LocalDateTime;

public interface CouponValidator {

    void verifyDiscountType(final String discountType);

    void verifyDiscountAmount(final int discountAmount);

    void verifyExpirationAt(final LocalDateTime expirationAt, final LocalDateTime currentTime);

    void verifyQuantity(final int quantity);
}
