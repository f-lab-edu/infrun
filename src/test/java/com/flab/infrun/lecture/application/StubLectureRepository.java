package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.repository.LectureRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

final class StubLectureRepository implements LectureRepository {

    private final Map<Long, Lecture> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Lecture save(final Lecture entity) {
        persistence.put(++sequence, entity);
        return entity;
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return Optional.ofNullable(persistence.get(id));
    }

    public Long getId() {
        return this.sequence;
    }

}
