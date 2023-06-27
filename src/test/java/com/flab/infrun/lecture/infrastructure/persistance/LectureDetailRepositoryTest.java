package com.flab.infrun.lecture.infrastructure.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureDetailRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureDetailRepositoryTest {

    @Autowired
    private LectureDetailRepository adapter;

    //todo-Transaction으로 처리
    @Test
    @DisplayName("강의상세 저장 테스트")
    void save() {
        LectureDetail lectureDetail = LectureDetail.of("1", "강의 A의 챕터1", 1L, 2L);

        LectureDetail saved = adapter.save(lectureDetail);
        Optional<LectureDetail> lecture1 = adapter.findById(saved.getId());

        assertThat(lecture1.get()).isEqualTo(saved);
    }
}
