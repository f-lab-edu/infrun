package com.flab.infrun.lecture.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;
import com.flab.infrun.lecture.infrastructure.persistence.mybatis.mapper.LectureMyBatisMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class LectureRepositoryTest {

    @Autowired
    private LectureMyBatisMapper mapper;

    @Test
    @DisplayName("강의 저장 테스트")
    void save() {
        Lecture lecture = Lecture.of("Lecture1", 20000, 1, "JAVA", "이것은 강의 A",
            1L);

        long saved = mapper.save(lecture);
        Optional<Lecture> lecture1 = mapper.findById((long) saved);

        assertThat(lecture1.get()).isEqualTo(lecture);
    }

    @Test
    @DisplayName("강의 디테일 검색 테스트")
    void findByDetail() {
        Lecture lecture = Lecture.of("Lecture1", 20000, 3, "JAVA", "이것은 강의 A",
            1L);

        LectureSearchForDetail lectureSearchForDetail = new LectureSearchForDetail("Lecture1", null,
            null, 3, "JAVA");
        mapper.save(lecture);
        List<Lecture> byDetail = mapper.findByDetail(lectureSearchForDetail);

        assertThat(byDetail.get(0)).isEqualTo(lecture);
    }
}
