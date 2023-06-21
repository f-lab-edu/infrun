package com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper;

import com.flab.infrun.lecture.domain.LectureDetail;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureDetailMyBatisMapper {

    int save(LectureDetail entity);

    Optional<LectureDetail> findById(Long id);
}
