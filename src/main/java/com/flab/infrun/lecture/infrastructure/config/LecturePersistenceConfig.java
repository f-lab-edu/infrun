package com.flab.infrun.lecture.infrastructure.config;

import com.flab.infrun.lecture.infrastructure.persistance.LectureJdbcTemplateRepository;
import com.flab.infrun.lecture.infrastructure.persistance.LectureRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistance.LectureVideoFileJdbcTemplateRepository;
import com.flab.infrun.lecture.infrastructure.persistance.LectureVideoFileRepositoryAdapter;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LecturePersistenceConfig {

    @Bean
    public LectureRepositoryAdapter lectureRepository(DataSource dataSource) {
        return new LectureRepositoryAdapter(new LectureJdbcTemplateRepository(dataSource));
    }

    @Bean
    public LectureVideoFileRepositoryAdapter lectureVideoFileRepository(DataSource dataSource) {
        return new LectureVideoFileRepositoryAdapter(
            new LectureVideoFileJdbcTemplateRepository(dataSource));
    }
}
