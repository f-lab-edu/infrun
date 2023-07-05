package com.flab.infrun.lecture.infrastructure.persistence.jpa;

import com.flab.infrun.lecture.domain.LectureFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureFileJpaRepository extends JpaRepository<LectureFile, Long> {

    @Override
    Optional<LectureFile> findById(Long aLong);

    @Override
    LectureFile save(LectureFile entity);
}
