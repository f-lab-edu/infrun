package com.flab.infrun.lecture.domain.repository;

import com.flab.infrun.lecture.domain.Lecture;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture entity);

    Optional<Lecture> findById(Long id);

    List<Lecture> findAllByIdIn(List<Long> lectureIds);
}
