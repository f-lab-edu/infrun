package com.flab.infrun.coupon.infrastructure;

import com.flab.infrun.common.exception.ErrorCode;
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
            throw new InvalidCouponDiscountTypeException(ErrorCode.INVALID_COUPON_DISCOUNT_TYPE);
        }
    }

    @Override
    public void verifyDiscountAmount(final int discountAmount) {
        if (discountAmount <= 0) {
            throw new InvalidCouponDiscountAmountException(
                ErrorCode.INVALID_COUPON_DISCOUNT_AMOUNT);
        }
    }

    @Override
    public void verifyExpirationAt(
        final LocalDateTime expirationAt,
        final LocalDateTime currentTime
    ) {
        if (expirationAt.isBefore(currentTime)) {
            throw new InvalidCouponExpirationAtException(ErrorCode.INVALID_COUPON_EXPIRATION_AT);
        }
    }

    @Override
    public void verifyQuantity(final int quantity) {
        if (quantity <= 0) {
            throw new InvalidCouponQuantityException(ErrorCode.INVALID_COUPON_QUANTITY);
        }
    }
}
