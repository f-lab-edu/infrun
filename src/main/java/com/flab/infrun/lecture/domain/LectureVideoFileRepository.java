package com.flab.infrun.lecture.domain;

import java.util.Optional;

public interface LectureVideoFileRepository {

    LectureVideoFile save(LectureVideoFile lectureVideoFile);

    Optional<LectureVideoFile> findById(Long id);
}
