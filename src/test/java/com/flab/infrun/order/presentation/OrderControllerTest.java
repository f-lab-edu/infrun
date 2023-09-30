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
import com.flab.infrun.order.application.result.PayedOrderResult;
import com.flab.infrun.order.domain.OrderStatus;
import com.flab.infrun.order.presentation.request.CreateOrderRequest;
import com.flab.infrun.order.presentation.request.PayOrderRequest;
import com.flab.infrun.payment.domain.PayMethod;
import com.flab.infrun.payment.domain.PayStatus;
import com.flab.infrun.payment.domain.PayType;
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

    @Test
    @DisplayName("주문을 생성하면 201 상태코드와 생성된 주문 정보를 반환한다")
    void createOrder_success() throws Exception {
        given(orderFacade.createOrder(any(), any()))
            .willReturn(createOrderResult());
        final var request = new CreateOrderRequest(List.of(1L, 2L), null);

        final var result = mockMvc.perform(post(ORDER_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.orderId").value(1L))
            .andExpect(jsonPath("$.data.totalPrice").value("90,000"))
            .andExpect(jsonPath("$.data.orderDate").exists())
            .andExpect(jsonPath("$.data.orderStatus").value("주문 생성"))
            .andExpect(jsonPath("$.data.orderItems").isNotEmpty())
            .andExpect(jsonPath("$.data.isCouponApplied").value(false));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCreateOrderRequest")
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

    private static Stream<Arguments> provideInvalidCreateOrderRequest() {
        return Stream.of(
            Arguments.of(new CreateOrderRequest(List.of(), null)),
            Arguments.of(new CreateOrderRequest(null, null))
        );
    }

    @Test
    @DisplayName("주문 결제가 성공하면 200 상태코드와 결제된 주문 정보를 반환한다")
    void payOrder_success() throws Exception {
        given(orderFacade.payOrder(any()))
            .willReturn(payedOrderResult());
        final var request = new PayOrderRequest(1L, BigDecimal.valueOf(90_000), "CARD", "LUMP_SUM",
            1);

        final ResultActions result = mockMvc.perform(post(ORDER_URI + "/pay")
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.amount").value("90000"))
            .andExpect(jsonPath("$.data.payMethod").value("카드"))
            .andExpect(jsonPath("$.data.payType").value("일시불"))
            .andExpect(jsonPath("$.data.orderStatus").value("주문 완료"))
            .andExpect(jsonPath("$.data.payStatus").value("결제 성공"))
            .andExpect(jsonPath("$.data.payedAt").exists());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPayOrderRequest")
    @DisplayName("주문 결제 요청이 잘못된 경우 400 상태코드와 에러 메시지를 반환한다")
    void payOrder_withInvalidRequest(final PayOrderRequest request) throws Exception {
        final var result = mockMvc.perform(post(ORDER_URI + "/pay")
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    private static Stream<Arguments> provideInvalidPayOrderRequest() {
        return Stream.of(
            Arguments.of(
                new PayOrderRequest(null, BigDecimal.valueOf(90_000), "CARD", "LUMP_SUM", 1)),
            Arguments.of(new PayOrderRequest(1L, null, "CARD", "LUMP_SUM", 1)),
            Arguments.of(new PayOrderRequest(1L, BigDecimal.valueOf(90_000), null, "LUMP_SUM", 1)),
            Arguments.of(new PayOrderRequest(1L, BigDecimal.valueOf(90_000), "ANY", "LUMP_SUM", 1)),
            Arguments.of(new PayOrderRequest(1L, BigDecimal.valueOf(90_000), "CARD", null, 1)),
            Arguments.of(new PayOrderRequest(1L, BigDecimal.valueOf(90_000), "CARD", "ANY", 1)),
            Arguments.of(new PayOrderRequest(1L, BigDecimal.valueOf(90_000), "CARD", "ANY", null)),
            Arguments.of(new PayOrderRequest(1L, BigDecimal.valueOf(90_000), "CARD", "ANY", -1))
        );
    }

    private UserAdapter createUser() {
        return new UserAdapter(Member.of("test", "test", "test"));
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

    private PayedOrderResult payedOrderResult() {
        return new PayedOrderResult(
            BigDecimal.valueOf(90_000),
            PayType.LUMP_SUM,
            PayMethod.CARD,
            OrderStatus.ORDER_COMPLETED.getDescription(),
            PayStatus.PAYMENT_SUCCESS.getDescription(),
            LocalDateTime.now()
        );
    }
}