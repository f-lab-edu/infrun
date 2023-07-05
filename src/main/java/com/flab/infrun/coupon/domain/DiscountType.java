package com.flab.infrun.coupon.domain;

import java.util.function.BiFunction;

public enum DiscountType {


    FIX(DiscountType::calculateDiscountByFix),
    RATE(DiscountType::calculateDiscountByRate);

    private final BiFunction<Integer, Integer, Integer> expression;

    DiscountType(final BiFunction<Integer, Integer, Integer> expression) {
        this.expression = expression;
    }

    private static int calculateDiscountByRate(final int lecturePrice, final int discountAmount) {
        final int discountedPrice = lecturePrice * (100 - discountAmount) / 100;
        return (int) (double) ((discountedPrice / 10) * 10);
    }

    private static int calculateDiscountByFix(final int lecturePrice, final int discountAmount) {
        return lecturePrice - discountAmount;
    }

    public int calculateDiscountPrice(final int lecturePrice, final int discountValue) {
        return expression.apply(lecturePrice, discountValue);
    }
}
