package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.LectureVideoFile;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class LectureVideoFileJdbcTemplateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public LectureVideoFileJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("file")
            .usingGeneratedKeyColumns("id");

    }

    public Optional<LectureVideoFile> findById(Long id) {
        String sql = "select id , name, url FROM file where id = :id";
        Map<String, Object> param = Map.of("id", id);
        LectureVideoFile lecture = jdbcTemplate.queryForObject(sql, param, itemRowMapper());
        return Optional.ofNullable(lecture);
    }

    private RowMapper<LectureVideoFile> itemRowMapper() {
        return (rs, rowNum) -> {
            LectureVideoFile lecture = LectureVideoFile.of(
                rs.getString("url"),
                rs.getString("name")
            );
            lecture.setId(rs.getLong("id"));
            return lecture;
        };
    }

    public LectureVideoFile save(LectureVideoFile entity) {

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(
            entity);
        Number key = jdbcInsert.executeAndReturnKey(param);
        entity.setId(key.longValue());
        return entity;
    }


}
