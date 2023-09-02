package com.flab.infrun.lecture.domain;

import com.flab.infrun.lecture.domain.repository.LectureReviewRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class StubLectureReviewRepository implements LectureReviewRepository {

    private final Map<Long, LectureReview> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public LectureReview save(final LectureReview entity) {
        persistence.put(++sequence, entity);
        entity.assignId(sequence);
        return entity;
    }

    @Override
    public Long deleteById(Long id) {
        int size = persistence.size();
        persistence.remove(id);

        return (long) (size - persistence.size());
    }

    @Override
    public List<LectureReview> findByLectureId(Long lectureId) {
        return persistence.values().stream()
            .filter(r -> r.getLecture().getId().equals(lectureId))
            .toList();
    }

    @Override
    public Optional<LectureReview> findById(Long id) {
        return Optional.ofNullable(persistence.get(id));
    }
}
