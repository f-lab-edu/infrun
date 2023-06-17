package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.domain.LectureVideoFileRepository;
import java.util.Optional;


public class LectureVideoFileRepositoryAdapter implements LectureVideoFileRepository {

    private final LectureVideoFileJdbcTemplateRepository jdbcTemplateRepository;

    public LectureVideoFileRepositoryAdapter(
        LectureVideoFileJdbcTemplateRepository lectureVideoFileJdbcTemplateRepository) {
        this.jdbcTemplateRepository = lectureVideoFileJdbcTemplateRepository;
    }

    @Override
    public LectureVideoFile save(LectureVideoFile lectureVideoFile) {
        return jdbcTemplateRepository.save(lectureVideoFile);
    }


    @Override
    public Optional<LectureVideoFile> findById(Long id) {
        return jdbcTemplateRepository.findById(id);
    }
}
