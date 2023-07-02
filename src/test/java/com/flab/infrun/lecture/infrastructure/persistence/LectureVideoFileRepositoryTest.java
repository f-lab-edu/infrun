package com.flab.infrun.lecture.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureFile;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureFileJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LectureVideoFileRepositoryTest {

    @Autowired
    private LectureFileJpaRepository jpaRepository;

    @Test
    @DisplayName("강의 파일 저장 테스트")
    void save() {
        LectureFile lectureVideoFile = LectureFile.of("/lecture", "파일명1.pdf");

        LectureFile saved = jpaRepository.save(lectureVideoFile);

        Optional<LectureFile> lecture1 = jpaRepository.findById(saved.getId());
        assertThat(lecture1.get()).isEqualTo(lectureVideoFile);
    }
}
