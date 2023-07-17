package com.flab.infrun.cart.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.cart.application.command.AddCartItemCommand;
import com.flab.infrun.cart.application.result.AddedCartItemResult;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartItem;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.lecture.domain.Lecture;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


final class AddCartItemProcessorTest {

    private AddCartItemProcessor sut;
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        final FakeLectureRepository lectureRepository = new FakeLectureRepository();
        cartRepository = new FakeCartRepository();
        sut = new AddCartItemProcessor(cartRepository, lectureRepository);

        createLectures(lectureRepository);
    }

    @Test
    @DisplayName("수강바구니를 생성하고 선택한 강의를 저장한다")
    void createCart() {
        final var command = CreteAddCartCommand();

        final var result = sut.execute(command);

        assertThat(result).isNotNull();
        assertThat(result.lectureIds()).hasSize(1);
        assertThat(result.totalPrice()).isEqualTo(BigDecimal.valueOf(79_000));
    }

    @Test
    @DisplayName("수강바구니가 이미 있다면, 강의를 추가한다")
    void addCartItem_cartExist() {
        final var cartItem = createCartItem();
        final var command = CreteAddCartCommand();

        final var result = sut.execute(command);

        assertThat(result)
            .extracting(AddedCartItemResult::totalPrice)
            .isEqualTo(BigDecimal.valueOf(63_000 + 79_000));
        assertThat(result.lectureIds()).hasSize(2);
    }

    private AddCartItemCommand CreteAddCartCommand() {
        return new AddCartItemCommand(1L, 2L);
    }

    private Cart createCartItem() {
        final Cart cart = cartRepository.save(Cart.create(1L));
        cart.addCartItem(CartItem.of(1L, BigDecimal.valueOf(63_000)));

        return cart;
    }

    private void createLectures(final FakeLectureRepository lectureRepository) {
        lectureRepository.saveAll(List.of(
            Lecture.of("JPA 강의", 53_000, 3, "JPA", "JPA 강의입니다.", 2L),
            Lecture.of("스프링 강의", 79_000, 3, "SPRING", "SPRING 강의입니다.", 2L),
            Lecture.of("JAVA 강의", 88_000, 3, "JAVA", "JAVA 강의입니다.", 2L)
        ));
    }
}
