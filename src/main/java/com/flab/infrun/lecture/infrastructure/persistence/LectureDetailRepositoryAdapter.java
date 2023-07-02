package com.flab.infrun.lecture.infrastructure.persistence;

import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureDetailRepository;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureDetailJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LectureDetailRepositoryAdapter implements
    LectureDetailRepository {

    private final LectureDetailJpaRepository lectureDetailJpaRepository;

    @Override
    public LectureDetail save(LectureDetail entity) {
        return lectureDetailJpaRepository.save(entity);
    }

    @Override
    public Optional<LectureDetail> findById(Long id) {
        return lectureDetailJpaRepository.findById(id);
    }

}
