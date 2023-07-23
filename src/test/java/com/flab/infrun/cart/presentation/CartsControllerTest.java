package com.flab.infrun.cart.presentation;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.cart.application.CartsFacade;
import com.flab.infrun.cart.application.result.AddedCartItemResult;
import com.flab.infrun.cart.application.result.CartsResult;
import com.flab.infrun.cart.application.result.CartsResult.CartItemResult;
import com.flab.infrun.cart.application.result.DeletedCartItemResult;
import com.flab.infrun.cart.presentation.request.AddCartItemRequest;
import com.flab.infrun.cart.presentation.request.DeleteCartItemRequest;
import com.flab.infrun.common.config.security.UserAdapter;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CartsController.class)
final class CartsControllerTest {

    private static final String CART_URI = "/carts";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private CartsFacade cartsFacade;

    @Test
    @DisplayName("수강바구니를 조회하면 200 상태코드와 수강바구니에 담긴 수강 목록을 반환한다")
    void getCartItem_success() throws Exception {
        given(cartsFacade.readCartItems(any()))
            .willReturn(createCartResult());

        final var result = mockMvc.perform(get(CART_URI)
            .with(user(createUser()))
        ).andDo(print());

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.carts").isNotEmpty())
            .andExpect(jsonPath("$.data.totalPrice").value(10_000))
            .andExpect(jsonPath("$.data.carts[0].lectureId").value(1))
            .andExpect(jsonPath("$.data.carts[0].lectureTitle").value("스프링 강의"))
            .andExpect(jsonPath("$.data.carts[0].teacherName").value("스프링신"))
            .andExpect(jsonPath("$.data.carts[0].price").value(10_000));
    }

    @Test
    @DisplayName("수강바구니에 수강을 추가하면 200 상태코드와 수강바구니에 담긴 수강 ID 목록을 반환한다")
    void addCartItem_success() throws Exception {
        final var request = createCorrectAddCartItemRequest();
        when(cartsFacade.addCartItem(any()))
            .thenReturn(AddedCartItemResult.from(BigDecimal.valueOf(10000), List.of(1L, 2L, 3L)));

        final var result = mockMvc.perform(post(CART_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalPrice").value(10000))
            .andExpect(jsonPath("$.data.lectureIds").isNotEmpty())
            .andExpect(jsonPath("$.data.lectureIds").value(containsInAnyOrder(1, 2, 3)));
    }

    @Test
    @DisplayName("수강바구니에 수강 추가 시 입력 값이 유효하지 않은 경우 400 에러코드와 예외를 반환한다")
    void addCartItem_withInvalidRequest() throws Exception {
        final var request = createInvalidAddCartItemRequest();

        final var result = mockMvc.perform(post(CART_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(createUser()))
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("수강바구니의 수강 삭제 시 200 상태코드와 수강바구니에 담긴 수강 ID 목록을 반환한다")
    void deleteCartItem_success() throws Exception {
        final var request = createCorrectDeleteCartItemRequest();
        when(cartsFacade.deleteCartItem(any()))
            .thenReturn(DeletedCartItemResult.from(BigDecimal.valueOf(5000), List.of(2L, 3L)));

        final var result = mockMvc.perform(delete(CART_URI + "/" + request.lectureId())
            .with(user(createUser()))
            .with(csrf())
        ).andDo(print());

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalPrice").value(5000))
            .andExpect(jsonPath("$.data.lectureIds").isNotEmpty())
            .andExpect(jsonPath("$.data.lectureIds").value(containsInAnyOrder(2, 3)));
    }


    private AddCartItemRequest createCorrectAddCartItemRequest() {
        return new AddCartItemRequest(1L);
    }

    private AddCartItemRequest createInvalidAddCartItemRequest() {
        return new AddCartItemRequest(null);
    }

    private DeleteCartItemRequest createCorrectDeleteCartItemRequest() {
        return new DeleteCartItemRequest(1L);
    }

    private UserAdapter createUser() {
        Member member = Member.of("test", "test", "test");
        memberRepository.save(member);

        return new UserAdapter(member);
    }

    private CartsResult createCartResult() {
        return new CartsResult(
            List.of(new CartItemResult(1L, "스프링 강의", "스프링신",
                BigDecimal.valueOf(10000))), BigDecimal.valueOf(10000));
    }
}