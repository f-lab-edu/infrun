package com.flab.infrun.lecture.domain;

import java.util.List;
import java.util.Optional;

public interface LectureReviewRepository {

    List<LectureReview> findByLectureId(Long lectureId);

    Optional<LectureReview> findById(Long id);

    LectureReview save(LectureReview entity);

    Long deleteById(Long id);
}
