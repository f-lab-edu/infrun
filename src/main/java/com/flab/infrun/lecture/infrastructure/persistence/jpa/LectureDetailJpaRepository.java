package com.flab.infrun.lecture.infrastructure.persistence.jpa;

import com.flab.infrun.lecture.domain.LectureDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureDetailJpaRepository extends JpaRepository<LectureDetail, Long> {

    @Override
    LectureDetail save(LectureDetail entity);

    @Override
    Optional<LectureDetail> findById(Long id);
}
