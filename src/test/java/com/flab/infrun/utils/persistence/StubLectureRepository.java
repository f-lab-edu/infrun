package com.flab.infrun.utils.persistence;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.repository.LectureRepository;
import java.util.List;
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

    @Override
    public List<Lecture> findAllByIdIn(List<Long> lectureIds) {
        return null;
    }
}
