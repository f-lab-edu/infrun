package com.flab.infrun.lecture.infrastructure.persistence;

import com.flab.infrun.lecture.domain.LectureFile;
import com.flab.infrun.lecture.domain.repository.LectureFileRepository;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureFileJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LectureFileRepositoryAdapter implements LectureFileRepository {

    private final LectureFileJpaRepository lectureVideoJpaRepository;

    @Override
    public LectureFile save(LectureFile lectureVideoFile) {
        return lectureVideoJpaRepository.save(lectureVideoFile);
    }

    @Override
    public Optional<LectureFile> findById(Long id) {
        return lectureVideoJpaRepository.findById(id);
    }
}
