package com.flab.infrun.lecture.domain.repository;

import com.flab.infrun.lecture.domain.LectureFile;
import java.util.Optional;

public interface LectureFileRepository {

    LectureFile save(LectureFile lectureVideoFile);

    Optional<LectureFile> findById(Long id);
}
