package com.flab.infrun.lecture.domain;

import java.util.Optional;

public interface LectureFileRepository {

    LectureFile save(LectureFile lectureVideoFile);

    Optional<LectureFile> findById(Long id);
}
