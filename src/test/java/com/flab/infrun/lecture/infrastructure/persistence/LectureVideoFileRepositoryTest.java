package com.flab.infrun.lecture.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.infrastructure.persistence.mybatis.mapper.LectureVideoFileMyBatisMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class LectureVideoFileRepositoryTest {

    @Autowired
    private LectureVideoFileMyBatisMapper mapper;

    @Test
    @DisplayName("강의 파일 저장 테스트")
    void save() {
        LectureVideoFile lectureVideoFile = LectureVideoFile.of("/lecture", "파일명1.pdf");

        long saved = mapper.save(lectureVideoFile);

        Optional<LectureVideoFile> lecture1 = mapper.findById(saved);
        assertThat(lecture1.get()).isEqualTo(lectureVideoFile);
    }
}
