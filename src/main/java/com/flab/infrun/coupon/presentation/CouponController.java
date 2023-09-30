package com.flab.infrun.coupon.presentation;

import com.flab.infrun.common.config.security.CurrentUser;
import com.flab.infrun.common.response.Response;
import com.flab.infrun.coupon.application.CouponFacade;
import com.flab.infrun.coupon.presentation.request.CreateCouponRequest;
import com.flab.infrun.coupon.presentation.request.EnrollCouponRequest;
import com.flab.infrun.coupon.presentation.response.CouponViewResponse;
import com.flab.infrun.coupon.presentation.response.CreatedCouponResponse;
import com.flab.infrun.coupon.presentation.response.EnrolledCouponResponse;
import com.flab.infrun.member.domain.Member;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponFacade facade;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'TEACHER')")
    @GetMapping
    public Response<CouponViewResponse> read(@CurrentUser final Member member) {
        final var result = facade.getCoupons(member.getId(), LocalDateTime.now());

        return Response.success(CouponViewResponse.from(result));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public Response<CreatedCouponResponse> create(
        @Valid @RequestBody final CreateCouponRequest request) {
        var result = facade.createCoupons(request.toCommand(), LocalDateTime.now());

        return Response.success(CreatedCouponResponse.from(result));
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'TEACHER')")
    @PostMapping("/enroll")
    public Response<EnrolledCouponResponse> enroll(
        @CurrentUser final Member member,
        @Valid @RequestBody final EnrollCouponRequest request
    ) {
        var result = facade.enrollCoupon(request.toCommand(member), LocalDateTime.now());

        return Response.success(EnrolledCouponResponse.from(result));
    }
}
