package com.flab.infrun.lecture.infrastructure.persistence.mybatis.mapper;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureMyBatisMapper {

    long save(Lecture entity);

    Optional<Lecture> findById(Long id);

    List<Lecture> findByDetail(LectureSearchForDetail detail);
}
