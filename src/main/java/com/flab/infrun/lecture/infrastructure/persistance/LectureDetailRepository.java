package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.LectureDetail;
import java.util.Optional;


public class LectureDetailRepository implements
    com.flab.infrun.lecture.domain.LectureDetailRepository {

    private final LectureDetailJdbcTemplateRepository lectureDetailJdbcTemplateRepository;

    public LectureDetailRepository(
        LectureDetailJdbcTemplateRepository lectureDetailJdbcTemplateRepository) {
        this.lectureDetailJdbcTemplateRepository = lectureDetailJdbcTemplateRepository;
    }

    @Override
    public LectureDetail save(LectureDetail entity) {
        return lectureDetailJdbcTemplateRepository.save(entity);
    }

    @Override
    public Optional<LectureDetail> findById(Long id) {
        return lectureDetailJdbcTemplateRepository.findById(id);
    }
}
