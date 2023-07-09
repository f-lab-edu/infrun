package com.flab.infrun.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.coupon.application.command.CouponRegisterCommand;
import com.flab.infrun.coupon.application.result.EnrolledCouponResult;
import com.flab.infrun.coupon.domain.Coupon;
import com.flab.infrun.coupon.domain.DiscountInfo;
import com.flab.infrun.coupon.domain.DiscountType;
import com.flab.infrun.coupon.infrastructure.persistence.CouponJpaRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcurrencyRegisterCouponTest {

    @Autowired
    private EnrollCouponProcessor sut;
    @Autowired
    private CouponJpaRepository couponRepository;
    @Autowired
    private MemberJpaRepository memberRepository;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAllInBatch();
        final String couponCode = "coupon";
        final LocalDateTime expirationAt = LocalDateTime.of(2100, 1, 1, 0, 0);
        couponRepository.save(
            Coupon.create(couponCode, DiscountInfo.of(DiscountType.FIX, 1000), expirationAt));
    }

    @RepeatedTest(30)
    @DisplayName("동시에 쿠폰을 등록할 경우 하나만 성공하고 나머지는 실패한다")
    void registerCoupon_when_concurrency() throws Exception {
        final String couponCode = "coupon";
        final int threadCount = 100;

        final var result = getConcurrencyResult(threadCount, couponCode);

        assertThat(result)
            .filteredOn(o -> o instanceof EnrolledCouponResult)
            .hasSize(1);
    }

    private List<Object> getConcurrencyResult(
        final int threadCount,
        final String couponCode
    ) throws InterruptedException, ExecutionException {
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        List<Future<Object>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final Member member = memberRepository.save(
                Member.of(
                    "user" + i,
                    "user" + i + "@test.com",
                    "1234"));
            final LocalDateTime currentTime = LocalDateTime.of(2023, 6, 30, 0, 0);
            final Future<Object> future = executorService.submit(() -> {
                try {
                    return sut.execute(
                        new CouponRegisterCommand(member, couponCode),
                        currentTime);
                } catch (Exception e) {
                    return e;
                } finally {
                    latch.countDown();
                }
            });

            futures.add(future);
        }

        latch.await();

        final List<Object> results = new ArrayList<>();
        for (Future<Object> future : futures) {
            results.add(future.get());
        }

        return results;
    }
}
