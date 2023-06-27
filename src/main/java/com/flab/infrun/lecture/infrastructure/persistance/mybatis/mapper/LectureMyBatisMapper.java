package com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureMyBatisMapper {

    int save(Lecture entity);

    Optional<Lecture> findById(Long id);

    Lecture findByDetail(LectureSearchForDetail detail);
}
