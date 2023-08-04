package com.flab.infrun.lecture.infrastructure.persistence;

import com.flab.infrun.lecture.domain.LectureReview;
import com.flab.infrun.lecture.domain.repository.LectureReviewRepository;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureReviewJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LectureReviewRepositoryAdapter implements LectureReviewRepository {

    private final LectureReviewJpaRepository jpaRepository;

    @Override
    public List<LectureReview> findByLectureId(Long lectureId) {
        return jpaRepository.findByLectureId(lectureId);
    }

    @Override
    public Optional<LectureReview> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public LectureReview save(LectureReview entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public void delete(LectureReview entity) {

    }

}
