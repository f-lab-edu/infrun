package com.flab.infrun.order.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.common.config.security.UserAdapter;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.order.application.OrderFacade;
import com.flab.infrun.order.application.result.CreatedOrderResult;
import com.flab.infrun.order.presentation.request.CreateOrderRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = OrderController.class)
final class OrderControllerTest {

    private static final String ORDER_URI = "/orders";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderFacade orderFacade;

    private static Stream<Arguments> provideCreateOrderRequest() {
        return Stream.of(
            Arguments.of(new CreateOrderRequest(List.of(), null)),
            Arguments.of(new CreateOrderRequest(null, null))
        );
    }

    @Test
    @DisplayName("주문을 생성하면 200 상태코드와 생성된 주문 정보를 반환한다")
    void createOrder_success() throws Exception {
        given(orderFacade.createOrder(any(), any()))
            .willReturn(createOrderResult());
        final var request = new CreateOrderRequest(List.of(1L, 2L), null);

        final ResultActions result = mockMvc.perform(post(ORDER_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.orderId").value(1L))
            .andExpect(jsonPath("$.data.totalPrice").value("90,000"))
            .andExpect(jsonPath("$.data.orderDate").exists())
            .andExpect(jsonPath("$.data.orderStatus").value("주문 생성"))
            .andExpect(jsonPath("$.data.orderItems").isNotEmpty())
            .andExpect(jsonPath("$.data.isCouponApplied").value(false));
    }

    @ParameterizedTest
    @MethodSource("provideCreateOrderRequest")
    @DisplayName("주문 생성 요청이 잘못된 경우 400 상태코드와 에러 메시지를 반환한다")
    void createOrder_withInvalidRequest(final CreateOrderRequest request) throws Exception {
        final var result = mockMvc.perform(post(ORDER_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    private CreatedOrderResult createOrderResult() {
        return new CreatedOrderResult(
            1L,
            BigDecimal.valueOf(90_000),
            BigDecimal.ZERO,
            LocalDateTime.now(),
            "주문 생성",
            List.of(
                new CreatedOrderResult.OrderItemResult(
                    "스프링의 신",
                    "스프링 강의",
                    BigDecimal.valueOf(30_000),
                    BigDecimal.ZERO
                ),
                new CreatedOrderResult.OrderItemResult(
                    "JPA의 신",
                    "JPA 강의",
                    BigDecimal.valueOf(30_000),
                    BigDecimal.ZERO
                ),
                new CreatedOrderResult.OrderItemResult(
                    "Java의 신",
                    "Java 강의",
                    BigDecimal.valueOf(30_000),
                    BigDecimal.ZERO
                )
            ),
            false);
    }

    private UserAdapter createUser() {
        return new UserAdapter(Member.of("test", "test", "test"));
    }
}