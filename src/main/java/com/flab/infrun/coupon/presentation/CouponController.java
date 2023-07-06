package com.flab.infrun.coupon.presentation;

import com.flab.infrun.common.response.Response;
import com.flab.infrun.coupon.application.CouponFacade;
import com.flab.infrun.coupon.presentation.request.CreateCouponRequest;
import com.flab.infrun.coupon.presentation.response.CouponResponse;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public Response<CouponResponse> create(@Valid @RequestBody final CreateCouponRequest request) {
        var result = facade.createCoupons(request.toCommand(), LocalDateTime.now());

        return Response.success(CouponResponse.from(result));
    }

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public String test() {
        return "TEST";
    }
}
