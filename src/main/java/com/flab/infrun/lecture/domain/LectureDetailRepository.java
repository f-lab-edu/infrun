package com.flab.infrun.lecture.domain;

import java.util.Optional;

public interface LectureDetailRepository {

    LectureDetail save(LectureDetail entity);

    Optional<LectureDetail> findById(Long id);
}
