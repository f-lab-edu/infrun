package com.flab.infrun.lecture.infrastructure.persistence.mybatis.mapper;

import com.flab.infrun.lecture.domain.Lecture;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureMyBatisMapper {

    int save(Lecture entity);

    Optional<Lecture> findById(Long id);
}
