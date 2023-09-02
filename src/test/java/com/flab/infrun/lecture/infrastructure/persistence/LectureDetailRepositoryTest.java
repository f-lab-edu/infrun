package com.flab.infrun.lecture.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureDetailJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LectureDetailRepositoryTest {

    @Autowired
    private LectureDetailJpaRepository repository;

    @Test
    @DisplayName("강의상세 저장 테스트")
    void save() {
        LectureDetail lectureDetail = LectureDetail.of("1", "강의 A의 챕터1",
            LectureFile.of("", "spring기초"));

        LectureDetail saved = repository.save(lectureDetail);
        Optional<LectureDetail> lecture1 = repository.findById(saved.getId());

        assertThat(lecture1.get()).isEqualTo(lectureDetail);
    }
}
