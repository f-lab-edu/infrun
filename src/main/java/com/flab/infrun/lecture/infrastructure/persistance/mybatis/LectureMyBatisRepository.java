package com.flab.infrun.lecture.infrastructure.persistance.mybatis;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper.LectureMyBatisMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LectureMyBatisRepository {

    private final LectureMyBatisMapper mapper;

    public Optional<Lecture> findById(Long id) {
        return mapper.findById(id);
    }

    public Lecture save(Lecture entity) {
        mapper.save(entity);
        return entity;
    }

    public List<Lecture> findByDetail(LectureSearchForDetail detail) {
        return mapper.findByDetail(detail);
    }
}
