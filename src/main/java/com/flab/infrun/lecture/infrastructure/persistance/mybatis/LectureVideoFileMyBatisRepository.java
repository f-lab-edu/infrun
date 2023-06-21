package com.flab.infrun.lecture.infrastructure.persistance.mybatis;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper.LectureVideoFileMyBatisMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LectureVideoFileMyBatisRepository {

    private final LectureVideoFileMyBatisMapper mapper;

    public Optional<LectureVideoFile> findById(Long id) {
        return mapper.findById(id);
    }

    public LectureVideoFile save(LectureVideoFile entity) {
        mapper.save(entity);
        return entity;
    }
}
