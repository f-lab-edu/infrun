package com.flab.infrun.lecture.infrastructure.persistence.jpa;

import com.flab.infrun.lecture.domain.LectureReview;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureReviewJpaRepository extends JpaRepository<LectureReview, Long> {

    @Override
    LectureReview save(LectureReview entity);

    @Override
    Optional<LectureReview> findById(Long id);

    List<LectureReview> findByLectureId(Long id);

}
