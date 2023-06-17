package com.flab.infrun.lecture.domain;

import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture entity);

    Optional<Lecture> findById(Long id);
}
