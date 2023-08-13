package com.flab.infrun.coupon.presentation.response;

import com.flab.infrun.coupon.application.result.CouponView;
import java.util.List;

public record CouponViewResponse(
    int quantity,
    List<CouponResponse> coupons
) {

    public static CouponViewResponse from(final List<CouponView> couponViews) {
        return new CouponViewResponse(
            couponViews.size(),
            couponViews.stream()
                .map(couponView -> new CouponResponse(
                    couponView.dDay(),
                    couponView.discountValue(),
                    couponView.discountType()
                ))
                .toList()
        );
    }

    public record CouponResponse(
        long dDay,
        int discountValue,
        String discountType
    ) {

    }


}
