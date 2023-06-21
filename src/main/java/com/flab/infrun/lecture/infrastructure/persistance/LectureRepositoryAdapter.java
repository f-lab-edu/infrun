package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.LectureMyBatisRepository;
import java.util.Optional;


public class LectureRepositoryAdapter implements LectureRepository {

    private final LectureMyBatisRepository lectureMyBatisRepository;

    public LectureRepositoryAdapter(LectureMyBatisRepository lectureMyBatisRepository) {
        this.lectureMyBatisRepository = lectureMyBatisRepository;
    }

    @Override
    public Lecture save(Lecture entity) {
        return lectureMyBatisRepository.save(entity);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureMyBatisRepository.findById(id);
    }
}
