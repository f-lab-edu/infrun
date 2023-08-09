package com.flab.infrun.cart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.flab.infrun.cart.application.result.CartsResult;
import com.flab.infrun.cart.domain.Cart;
import com.flab.infrun.cart.domain.CartRepository;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
final class CartsReaderTest {

    @InjectMocks
    private CartsReader sut;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private LectureRepository lectureRepository;

    @Test
    @DisplayName("수강바구니를 조회한다")
    void readCartItems() {
        final Long ownerId = 1L;
        given(cartRepository.findByOwnerId(anyLong()))
            .willReturn(Optional.of(createCart()));
        given(lectureRepository.findAllByIdIn(anyList()))
            .willReturn(List.of(createLecture()));
        given(memberRepository.findById(anyLong()))
            .willReturn(createMember());

        final CartsResult result = sut.read(ownerId);

        assertThat(result.cartItemsResults()).hasSize(1);
        assertThat(result.cartItemsResults().get(0).lectureId()).isEqualTo(1L);
        assertThat(result.cartItemsResults().get(0).lectureTitle()).isEqualTo("JPA 강의");
        assertThat(result.cartItemsResults().get(0).teacherName()).isEqualTo("test");
        assertThat(result.cartItemsResults().get(0).price()).isEqualTo(BigDecimal.valueOf(53_000));
    }

    private Lecture createLecture() {
        final Member member = createMember();
        final Lecture lecture = Lecture.of(
            "JPA 강의",
            53_000,
            3,
            "JPA",
            "JPA 강의입니다.",
            member
        );
        lecture.assignId(1L);

        return lecture;
    }

    private Member createMember() {
        final Member member = Member.of("test", "test@test.com", "1234");
        member.assignId(1L);

        return member;
    }

    private Cart createCart() {
        return Cart.create(1L);
    }
}