package com.flab.infrun.lecture.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LectureRepositoryTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Test
    @DisplayName("강의 저장 테스트")
    void save() {
        Lecture lecture = Lecture.of("Lecture1", 20000, 1, "JAVA", "이것은 강의 A",
            null);

        Lecture saved = lectureJpaRepository.save(lecture);
        Optional<Lecture> lecture1 = lectureJpaRepository.findById(saved.getId());

        assertThat(lecture1.get()).isEqualTo(lecture);
    }
}
