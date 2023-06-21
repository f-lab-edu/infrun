package com.flab.infrun.lecture.infrastructure.persistance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureVideoFileJdbcTemplateRepositoryTest {

    @Autowired
    private LectureVideoFileRepositoryAdapter adapter;

    //todo-Transaction으로 처리
    @Test
    @DisplayName("강의 저장 테스트")
    void save() {
        LectureVideoFile lectureVideoFile = LectureVideoFile.of("/lecture", "파일명1.pdf");

        LectureVideoFile saved = adapter.save(lectureVideoFile);

        Optional<LectureVideoFile> lecture1 = adapter.findById(saved.getId());
        assertThat(saved).isEqualTo(lecture1.get());
    }
}
