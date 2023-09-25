package com.flab.infrun.coupon.infrastructure;

import com.flab.infrun.coupon.domain.CouponValidator;
import com.flab.infrun.coupon.domain.exception.InvalidCouponDiscountAmountException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponDiscountTypeException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponExpirationAtException;
import com.flab.infrun.coupon.domain.exception.InvalidCouponQuantityException;
import java.time.LocalDateTime;

public class CouponValidatorImpl implements CouponValidator {

    @Override
    public void verifyDiscountType(final String discountType) {
        if (discountType == null) {
            throw new InvalidCouponDiscountTypeException();
        }
    }

    @Override
    public void verifyDiscountValue(final int discountAmount) {
        if (discountAmount <= 0) {
            throw new InvalidCouponDiscountAmountException();
        }
    }

    @Override
    public void verifyExpirationAt(
        final LocalDateTime expirationAt,
        final LocalDateTime currentTime
    ) {
        if (expirationAt.isBefore(currentTime)) {
            throw new InvalidCouponExpirationAtException();
        }
    }

    @Override
    public void verifyQuantity(final int quantity) {
        if (quantity <= 0) {
            throw new InvalidCouponQuantityException();
        }
    }
}
