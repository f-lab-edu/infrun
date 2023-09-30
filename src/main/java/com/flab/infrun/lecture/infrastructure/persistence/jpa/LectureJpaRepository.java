package com.flab.infrun.lecture.infrastructure.persistence.jpa;

import com.flab.infrun.lecture.domain.Lecture;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    @Override
    Optional<Lecture> findById(Long aLong);

    @Override
    Lecture save(Lecture entity);

    List<Lecture> findAllByIdIn(List<Long> lectureIds);
}
