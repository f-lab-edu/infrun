package com.flab.infrun.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

import com.flab.infrun.coupon.application.result.CouponView;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
final class CouponReaderTest {

    @Mock
    private CouponReader sut;

    @Test
    @DisplayName("쿠폰 목록을 조회한다")
    void readCoupons() {
        final LocalDateTime currentTime = LocalDateTime.of(2023, 12, 31, 0, 0);
        final Long ownerId = 1L;
        given(sut.read(ownerId, currentTime))
            .willReturn(List.of(
                new CouponView(1, 10, "FIX"),
                new CouponView(1, 10000, "RATE")
            ));

        final var result = sut.read(ownerId, currentTime);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting("dDay", "discountValue", "discountType")
            .containsExactly(
                tuple(1L, 10, "FIX"),
                tuple(1L, 10000, "RATE")
            );
    }
}