package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import java.util.Optional;


public class LectureRepositoryAdapter implements LectureRepository {

    private final LectureJdbcTemplateRepository lectureJdbcRepository;

    public LectureRepositoryAdapter(LectureJdbcTemplateRepository lectureJdbcRepository) {
        this.lectureJdbcRepository = lectureJdbcRepository;
    }

    @Override
    public Lecture save(Lecture entity) {
        return lectureJdbcRepository.save(entity);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJdbcRepository.findById(id);
    }

}
