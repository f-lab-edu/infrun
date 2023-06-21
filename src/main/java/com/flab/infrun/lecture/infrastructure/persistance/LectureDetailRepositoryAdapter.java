package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureDetailRepository;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.LectureDetailMyBatisRepository;
import java.util.Optional;


public class LectureDetailRepositoryAdapter implements
    LectureDetailRepository {

    private final LectureDetailMyBatisRepository myBatisRepository;

    public LectureDetailRepositoryAdapter(
        LectureDetailMyBatisRepository myBatisRepository) {
        this.myBatisRepository = myBatisRepository;
    }

    @Override
    public LectureDetail save(LectureDetail entity) {
        return myBatisRepository.save(entity);
    }

    @Override
    public Optional<LectureDetail> findById(Long id) {
        return myBatisRepository.findById(id);
    }

}
