package com.flab.infrun.lecture.domain.repository;

import com.flab.infrun.lecture.domain.LectureReview;
import java.util.List;

public interface LectureReviewRepository {

    List<LectureReview> findByLectureId(Long lectureId);

    LectureReview save(LectureReview entity);

    void delete(LectureReview entity);

}
