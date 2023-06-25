package com.flab.infrun.lecture.infrastructure.config;

import com.flab.infrun.lecture.infrastructure.persistance.LectureDetailRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistance.LectureRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistance.LectureVideoFileRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.LectureDetailMyBatisRepository;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.LectureMyBatisRepository;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.LectureVideoFileMyBatisRepository;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper.LectureDetailMyBatisMapper;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper.LectureMyBatisMapper;
import com.flab.infrun.lecture.infrastructure.persistance.mybatis.mapper.LectureVideoFileMyBatisMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LecturePersistenceConfig {

    @Bean
    public LectureRepositoryAdapter lectureRepository(LectureMyBatisMapper mapper) {
        return new LectureRepositoryAdapter(new LectureMyBatisRepository(mapper));
    }

    @Bean
    public LectureDetailRepositoryAdapter lectureDetailRepository(
        LectureDetailMyBatisMapper mapper) {
        return new LectureDetailRepositoryAdapter(
            new LectureDetailMyBatisRepository(mapper));
    }

    @Bean
    public LectureVideoFileRepositoryAdapter lectureVideoFileRepository(
        LectureVideoFileMyBatisMapper mapper) {
        return new LectureVideoFileRepositoryAdapter(
            new LectureVideoFileMyBatisRepository(mapper));
    }
}