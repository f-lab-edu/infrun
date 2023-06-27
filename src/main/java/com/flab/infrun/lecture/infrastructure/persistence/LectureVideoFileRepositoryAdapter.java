package com.flab.infrun.lecture.infrastructure.persistence;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.domain.LectureVideoFileRepository;
import com.flab.infrun.lecture.infrastructure.persistence.mybatis.LectureVideoFileMyBatisRepository;
import java.util.Optional;


public class LectureVideoFileRepositoryAdapter implements LectureVideoFileRepository {

    private final LectureVideoFileMyBatisRepository lectureVideoFileMyBatisRepository;

    public LectureVideoFileRepositoryAdapter(
        LectureVideoFileMyBatisRepository lectureVideoFileMyBatisRepository) {
        this.lectureVideoFileMyBatisRepository = lectureVideoFileMyBatisRepository;
    }

    @Override
    public LectureVideoFile save(LectureVideoFile lectureVideoFile) {
        return lectureVideoFileMyBatisRepository.save(lectureVideoFile);
    }

    @Override
    public Optional<LectureVideoFile> findById(Long id) {
        return lectureVideoFileMyBatisRepository.findById(id);
    }
}
