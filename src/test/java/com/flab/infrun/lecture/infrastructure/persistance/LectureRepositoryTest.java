package com.flab.infrun.lecture.infrastructure.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;
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

        Lecture savedId = adapter.save(lecture);
        Optional<Lecture> lecture1 = adapter.findById(savedId.getId());

        assertThat(lecture1.get()).isEqualTo(lecture);
    }

    @Test
    @DisplayName("강의 디테일 검색 테스트")
    void findByDetail() {
        Lecture lecture = Lecture.of("Lecture1", 20000, 3, "JAVA", "이것은 강의 A",
            1L);

//        LectureSearchForDetail lectureSearchForDetail = new LectureSearchForDetail("Lecture1", null,
//            null, 3, "JAVA");
        LectureSearchForDetail lectureSearchForDetail = new LectureSearchForDetail(null, null,
            null, null, null);
        Lecture savedId = adapter.save(lecture);
        Lecture byDetail = adapter.findByDetail(lectureSearchForDetail);

        assertThat(byDetail).isEqualTo(lecture);
    }
}
