package com.flab.infrun.lecture.domain;

import com.flab.infrun.lecture.domain.repository.LectureRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class StubLectureRepository implements LectureRepository {

    private final Map<Long, Lecture> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Lecture save(Lecture entity) {
        persistence.put(++sequence, entity);
        entity.assignId(sequence);
        return entity;
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return Optional.ofNullable(persistence.get(id));
    }
}
