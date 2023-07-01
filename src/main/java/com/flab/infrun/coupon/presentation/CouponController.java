package com.flab.infrun.coupon.presentation;

import com.flab.infrun.coupon.application.CouponFacade;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping
    public void create(@Valid @RequestBody final CreateCouponRequest request) {
        var result = facade.createCoupons(request.toCommand(), LocalDateTime.now());

//        return Response.success(result);
    }
}
