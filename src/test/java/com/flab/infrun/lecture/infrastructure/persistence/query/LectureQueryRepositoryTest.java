package com.flab.infrun.lecture.infrastructure.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureFile;
import com.flab.infrun.lecture.infrastructure.data.LectureDTO;
import com.flab.infrun.lecture.infrastructure.persistence.query.condition.LectureSearchCondition;
import com.flab.infrun.member.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class LectureQueryRepositoryTest {

    @Autowired
    EntityManager em;
    LectureQueryRepository repository;
    Member member1;
    Member member2;

    @Test
    @Transactional
    @DisplayName("Fetch Join Test")
    void fetch_join_test() {
        LectureSearchCondition searchCondition1 = new LectureSearchCondition("LectureA",
            20000, 1, "JAVA", member1.getNickname());
        LectureSearchCondition searchCondition2 = new LectureSearchCondition(null, null, null, null,
            null);

        List<LectureDTO> searched1 = repository.search(searchCondition1);
        List<LectureDTO> searched2 = repository.search(searchCondition2);

        assertThat(searched1.size()).isEqualTo(1);
        assertThat(searched2.size()).isEqualTo(2);

        for (LectureDTO lectureDTO : searched1) {
            System.out.println(lectureDTO);
        }
        System.out.println("+++++++++++++++++++");

        for (LectureDTO lectureDTO : searched2) {
            System.out.println(lectureDTO);
        }
    }

    @BeforeEach
    void dataPush() {
        repository = new LectureQueryRepository(new JPAQueryFactory(em));

        member1 = Member.of("LDK", "ledk123@naver.com", "123");
        member2 = Member.of("LSA", "sian123@google.com", "456");

        Lecture lecture1 = Lecture.of("LectureA", 20000, 1, "JAVA", "이것은 강의 A",
            member1);
        List<LectureDetail> lectureDetailList1 = List.of(
            LectureDetail.of("1", "강의 A의 챕터1",
                LectureFile.of("", "JAVA 설정")),
            LectureDetail.of("1", "강의 A의 챕터1",
                LectureFile.of("", "JAVA 기초")),
            LectureDetail.of("2", "강의 A의 챕터2",
                LectureFile.of("", "JAVA 기본"))
        );
        lecture1.addLectureDetail(lectureDetailList1);

        Lecture lecture2 = Lecture.of("LectureB", 30000, 3, "SPRING", "이것은 스프링 강의",
            member2);
        List<LectureDetail> lectureDetailList2 = List.of(
            LectureDetail.of("1", "스프링 강의의 챕터1-1",
                LectureFile.of("", "spring 이란?")),
            LectureDetail.of("1", "스프링 강의의 챕터1-2",
                LectureFile.of("", "spring 5대 원칙")),
            LectureDetail.of("2", "스프링 강의의 챕터2-1",
                LectureFile.of("", "spring 기본"))
        );
        lecture2.addLectureDetail(lectureDetailList2);

        em.persist(member1);
        em.persist(lecture1);
        em.persist(member2);
        em.persist(lecture2);
        em.flush();
    }
}