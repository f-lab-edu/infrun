package com.flab.infrun.coupon.application;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.application.result.CouponRegisteredResult;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.CouponCodeGenerator;
import com.flab.infrun.coupon.domain.CouponRepository;
import com.flab.infrun.coupon.domain.CouponValidator;
import com.flab.infrun.coupon.domain.exception.NotFoundCouponException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CouponProcessor {

    private final CouponRepository couponRepository;
    private final CouponCodeGenerator couponCodeGenerator;
    private final CouponValidator couponValidator;

    public CouponProcessor(
        final CouponRepository couponRepository,
        final CouponCodeGenerator couponCodeGenerator,
        final CouponValidator couponValidator
    ) {
        this.couponRepository = couponRepository;
        this.couponCodeGenerator = couponCodeGenerator;
        this.couponValidator = couponValidator;
    }

    @Transactional
    public CreatedCouponResult createCoupons(
        final CreateCouponCommand command,
        final LocalDateTime currentTime
    ) {
        verifyCouponCommand(command, currentTime);

        final List<Coupon> coupons = generateCoupons(command);
        final List<Coupon> savedCoupons = couponRepository.saveAll(coupons);

        return CreatedCouponResult.from(
            savedCoupons.size(),
            savedCoupons.stream().map(Coupon::getCode).toList(),
            command.expirationAt()
        );
    }

    @Transactional
    public CouponRegisteredResult registerCoupon(
        final CouponRegisterCommand command,
        final LocalDateTime currentTime
    ) {
        final Coupon coupon = couponRepository.findByCouponCode(command.couponCode())
            .orElseThrow(() -> new NotFoundCouponException(ErrorCode.NOT_FOUND_COUPON));

        coupon.register(command.member(), currentTime);

        return CouponRegisteredResult.from(command.member().getEmail(), coupon);
    }

    private List<Coupon> generateCoupons(final CreateCouponCommand command) {
        final int quantity = command.quantity();

        return IntStream.range(0, quantity)
            .mapToObj(i -> command.toEntity(couponCodeGenerator.generate()))
            .toList();
    }

    private void verifyCouponCommand(final CreateCouponCommand command,
        final LocalDateTime currentTime) {
        couponValidator.verifyDiscountType(command.discountType());
        couponValidator.verifyDiscountAmount(command.discountAmount());
        couponValidator.verifyExpirationAt(command.expirationAt(), currentTime);
        couponValidator.verifyQuantity(command.quantity());
    }
}
