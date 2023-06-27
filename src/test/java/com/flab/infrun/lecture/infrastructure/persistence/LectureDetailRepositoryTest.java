package com.flab.infrun.lecture.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.infrastructure.persistence.mybatis.mapper.LectureDetailMyBatisMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class LectureDetailRepositoryTest {

    @Autowired
    private LectureDetailMyBatisMapper mapper;

    //todo-Transaction으로 처리
    @Test
    @DisplayName("강의상세 저장 테스트")
    void save() {
        LectureDetail lectureDetail = LectureDetail.of("1", "강의 A의 챕터1", 1L, 2L);

        int saved = mapper.save(lectureDetail);
        Optional<LectureDetail> lecture1 = mapper.findById((long) saved);

        assertThat(lecture1.get()).isEqualTo(lectureDetail);
    }
}
