package com.flab.infrun.lecture.infrastructure.persistance.mybatis;

import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper.LectureDetailMyBatisMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LectureDetailMyBatisRepository {

    private final LectureDetailMyBatisMapper mapper;

    public Optional<LectureDetail> findById(Long id) {
        return mapper.findById(id);
    }

    public LectureDetail save(LectureDetail entity) {
        mapper.save(entity);
        return entity;
    }

}
