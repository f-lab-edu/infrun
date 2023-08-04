package com.flab.infrun.lecture.domain.repository;

import com.flab.infrun.lecture.domain.LectureReview;
import java.util.List;
import java.util.Optional;

public interface LectureReviewRepository {

    List<LectureReview> findByLectureId(Long lectureId);

    Optional<LectureReview> findById(Long id);

    LectureReview save(LectureReview entity);

    void delete(LectureReview entity);

}
