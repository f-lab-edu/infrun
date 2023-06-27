package com.flab.infrun.lecture.infrastructure.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.Lecture;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureRepositoryTest {

    @Autowired
    private LectureRepositoryAdapter adapter;

    //todo-Transaction으로 처리
    @Test
    @DisplayName("강의 저장 테스트")
    void save() {
        Lecture lecture = Lecture.of("Lecture1", 20000, 1, "JAVA", "이것은 강의 A",
            1L);

        Lecture saved = adapter.save(lecture);
        Optional<Lecture> lecture1 = adapter.findById(saved.getId());

        assertThat(lecture1.get()).isEqualTo(lecture);
    }
}
