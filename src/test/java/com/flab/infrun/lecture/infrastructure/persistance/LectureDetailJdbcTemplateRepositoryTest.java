package com.flab.infrun.lecture.infrastructure.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureDetail;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureDetailJdbcTemplateRepositoryTest {

    @Autowired
    private LectureDetailRepositoryAdapter repositoryAdapter;

    //todo-Transaction으로 처리
    @Test
    @DisplayName("강의상세 저장 테스트")
    void save() {
        //given
        LectureDetail lectureDetail = LectureDetail.of("1", "강의 A의 챕터1", 1L);

        //when
        LectureDetail saved = repositoryAdapter.save(lectureDetail);

        //then
        Optional<LectureDetail> lecture1 = repositoryAdapter.findById(saved.getId());
        assertThat(saved).isEqualTo(lecture1.get());
    }
}