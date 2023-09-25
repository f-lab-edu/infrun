package com.flab.infrun.coupon.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.infrun.common.config.security.UserAdapter;
import com.flab.infrun.coupon.application.CouponFacade;
import com.flab.infrun.coupon.application.command.CreateCouponCommand;
import com.flab.infrun.coupon.application.result.CouponView;
import com.flab.infrun.coupon.application.result.CreatedCouponResult;
import com.flab.infrun.coupon.presentation.request.CreateCouponRequest;
import com.flab.infrun.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CouponController.class)
final class CouponControllerTest {

    private static final String COUPON_URI = "/coupons";
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CouponFacade couponFacade;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("쿠폰을 조회하면 200 상태코드와 쿠폰 리스트를 반환한다")
    void getCoupons() throws Exception {
        given(couponFacade.getCoupons(anyLong(), any(LocalDateTime.class)))
            .willReturn(createCouponResult());

        final var result = mockMvc.perform(get(COUPON_URI)
                .with(user(createUser()))
            )
            .andDo(print());

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.quantity").value(2))
            .andExpect(jsonPath("$.data.coupons").isNotEmpty());
    }

    private List<CouponView> createCouponResult() {
        return List.of(
            new CouponView(1L, 1000, "FIX"),
            new CouponView(1L, 10, "RATE"));
    }

    @Test
    @DisplayName("쿠폰 생성이 성공하면 201 상태코드와 쿠폰 코드 리스트를 반환한다")
    void createCoupon_success() throws Exception {
        final var request = createCorrectCouponRequest();
        given(couponFacade.createCoupons(any(CreateCouponCommand.class), any(LocalDateTime.class)))
            .willReturn(CreatedCouponResult.from(
                request.quantity(),
                List.of("couponCode1", "couponCode2"),
                LocalDateTime.of(2029, 12, 31, 0, 0, 0)));

        final var result = mockMvc.perform(post(COUPON_URI)
            .with(user(createUser()))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.data.quantity").value(request.quantity()));
        result.andExpect(jsonPath("$.data.couponCodes").isNotEmpty());
        result.andExpect(jsonPath("$.data.expirationAt").exists());
    }

    @Test
    @DisplayName("쿠폰 생성 시 입력 값이 유효하지 않은 경우 400 에러 코드와 메시지를 반환한다")
    void createCoupon_withInvalidRequest() throws Exception {
        final var request = createInvalidCouponRequest();

        final var result = mockMvc.perform(post(COUPON_URI)
            .with(user(createUser()))
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.message").exists());
    }

    private CreateCouponRequest createCorrectCouponRequest() {
        return new CreateCouponRequest(
            "FIX",
            2000,
            LocalDateTime.of(2029, 12, 31, 0, 0, 0),
            2
        );
    }

    private CreateCouponRequest createInvalidCouponRequest() {
        return new CreateCouponRequest(
            "NOTHING",
            0,
            LocalDateTime.of(1999, 12, 31, 0, 0, 0),
            0
        );
    }

    private UserAdapter createUser() {
        Member member = Member.of("test", "test", "test");
        member.assignId(1L);

        return new UserAdapter(member);
    }
}
