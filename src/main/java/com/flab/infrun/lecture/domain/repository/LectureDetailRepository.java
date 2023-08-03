package com.flab.infrun.lecture.domain.repository;

import com.flab.infrun.lecture.domain.LectureDetail;
import java.util.Optional;

public interface LectureDetailRepository {

    LectureDetail save(LectureDetail entity);

    Optional<LectureDetail> findById(Long id);
}
