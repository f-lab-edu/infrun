package com.flab.infrun.lecture.infrastructure.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.Lecture;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureJdbcTemplateRepositoryTest {

    @Autowired
    private LectureRepositoryAdapter lectureRepositoryAdapter;

    @Test
    @DisplayName("강의 저장 테스트")
    void save() {
        //given
        Lecture lecture = Lecture.of("lectureA", 20000, "이것은 강의 A");

        //when
        Lecture saved = lectureRepositoryAdapter.save(lecture);

        //then
        Optional<Lecture> lecture1 = lectureRepositoryAdapter.findById(saved.getId());
        assertThat(saved).isEqualTo(lecture1.get());
    }
}