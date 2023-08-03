package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.domain.LectureReview;
import com.flab.infrun.lecture.domain.repository.LectureReviewRepository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class StubLectureReviewRepository implements LectureReviewRepository {

    private final Map<Long, LectureReview> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public LectureReview save(final LectureReview entity) {
        persistence.put(++sequence, entity);
        return entity;
    }

    @Override
    public List<LectureReview> findByLectureId(Long lectureId) {
        return persistence.values().stream()
            .filter(r -> r.getLecture().getId().equals(lectureId))
            .toList();
    }

    @Override
    public void delete(LectureReview entity) {
        persistence.remove(entity.getId());
    }

    public Long getId() {
        return this.sequence;
    }
}
