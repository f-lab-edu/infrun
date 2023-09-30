package com.flab.infrun.cart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.cart.application.command.DeleteCartItemCommand;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.cart.domain.exception.NotFoundCartException;
import com.flab.infrun.cart.domain.exception.NotFoundCartItemException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class DeleteCartItemProcessorTest {

    private DeleteCartItemProcessor sut;
    private CartRepository cartRepository;

    @BeforeEach
    void setup() {
        cartRepository = new FakeCartRepository();
        sut = new DeleteCartItemProcessor(cartRepository);
    }

    @Test
    @DisplayName("수강바구니에서 선택한 강의를 삭제한다")
    void deleteCartItem() {
        createCartItem();
        final var command = createDeleteCartCommand();

        final var result = sut.execute(command);

        assertThat(result.lectureIds()).hasSize(0);
    }

    @Test
    @DisplayName("수강바구니가 없다면 예외가 발생한다")
    void deleteCartItem_notFoundCart() {
        final var command = createDeleteCartCommand();

        assertThatThrownBy(() -> sut.execute(command))
            .isInstanceOf(NotFoundCartException.class);
    }

    @Test
    @DisplayName("수강바구니에 해당 강의가 없다면 예외가 발생한다")
    void deleteCartItem_notFoundCartItem() {
        createCart();
        final var command = createDeleteCartCommand();

        assertThatThrownBy(() -> sut.execute(command))
            .isInstanceOf(NotFoundCartItemException.class);
    }

    private DeleteCartItemCommand createDeleteCartCommand() {
        return new DeleteCartItemCommand(1L, 1L);
    }

    private Cart createCartItem() {
        final Cart cart = cartRepository.save(Cart.create(1L));
        cart.addCartItem(1L, BigDecimal.valueOf(63_000));

        return cart;
    }

    private void createCart() {
        cartRepository.save(Cart.create(1L));
    }
}