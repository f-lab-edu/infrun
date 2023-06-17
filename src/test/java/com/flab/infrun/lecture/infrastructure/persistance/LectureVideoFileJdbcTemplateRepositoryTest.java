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

    @Test
    @DisplayName("강의 검색/저장 테스트")
    void save() {
        //given
        LectureVideoFile lectureVideoFile = LectureVideoFile.of("/lecture", "파일명1.pdf");

        //when
        LectureVideoFile saved = adapter.save(lectureVideoFile);

        //then
        Optional<LectureVideoFile> lecture1 = adapter.findById(saved.getId());
        assertThat(saved).isEqualTo(lecture1.get());

    }
}