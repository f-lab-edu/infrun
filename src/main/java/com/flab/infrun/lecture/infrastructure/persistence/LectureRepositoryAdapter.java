package com.flab.infrun.lecture.infrastructure.persistence;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureJpaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LectureRepositoryAdapter implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture save(Lecture entity) {
        return lectureJpaRepository.save(entity);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJpaRepository.findById(id);
    }

    @Override
    public List<Lecture> findAllByIdIn(final List<Long> lectureIds) {
        return lectureJpaRepository.findAllByIdIn(lectureIds);
    }
}
