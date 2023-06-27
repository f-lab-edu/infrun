package com.flab.infrun.lecture.domain;

import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;
import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture entity);

    Optional<Lecture> findById(Long id);

    Lecture findByDetail(LectureSearchForDetail detail);
}
