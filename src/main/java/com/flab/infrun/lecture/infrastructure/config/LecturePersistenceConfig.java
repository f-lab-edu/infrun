package com.flab.infrun.lecture.infrastructure.config;

import com.flab.infrun.lecture.infrastructure.persistence.LectureDetailRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistence.LectureFileRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistence.LectureRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureDetailJpaRepository;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureFileJpaRepository;
import com.flab.infrun.lecture.infrastructure.persistence.jpa.LectureJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LecturePersistenceConfig {

    @Bean
    public LectureRepositoryAdapter lectureRepository(final LectureJpaRepository jpaRepository) {
        return new LectureRepositoryAdapter(jpaRepository);
    }

    @Bean
    public LectureDetailRepositoryAdapter lectureDetailRepository(
        final LectureDetailJpaRepository jpaRepository) {
        return new LectureDetailRepositoryAdapter(jpaRepository);
    }

    @Bean
    public LectureFileRepositoryAdapter lectureVideoFileRepository(
        final LectureFileJpaRepository jpaRepository) {
        return new LectureFileRepositoryAdapter(jpaRepository);
    }
}
