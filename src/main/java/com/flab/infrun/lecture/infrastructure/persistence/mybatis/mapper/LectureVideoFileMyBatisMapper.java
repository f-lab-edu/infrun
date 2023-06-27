package com.flab.infrun.lecture.infrastructure.persistence.mybatis.mapper;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureVideoFileMyBatisMapper {

    int save(LectureVideoFile entity);

    Optional<LectureVideoFile> findById(Long id);
}
