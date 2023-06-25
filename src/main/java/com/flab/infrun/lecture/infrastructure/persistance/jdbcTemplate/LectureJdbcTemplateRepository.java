package com.flab.infrun.lecture.infrastructure.persistance.jdbcTemplate;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureLevel;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class LectureJdbcTemplateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public LectureJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("lecture")
            .usingGeneratedKeyColumns("id");

    }

    public Optional<Lecture> findById(Long id) {
        String sql = "select id , name, price, introduce FROM LECTURE where id = :id";
        Map<String, Object> param = Map.of("id", id);
        Lecture lecture = jdbcTemplate.queryForObject(sql, param, itemRowMapper());
        return Optional.ofNullable(lecture);
    }

    private RowMapper<Lecture> itemRowMapper() {
        return (rs, rowNum) -> {
            Lecture lecture = Lecture.of(
                rs.getString("name"),
                rs.getInt("price"),
                LectureLevel.valueOf(rs.getString("level")),
                rs.getString("skill"),
                rs.getString("introduce")
            );
            lecture.setId(rs.getLong("id"));
            return lecture;
        };
    }

    public Lecture save(Lecture entity) {

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(
            entity);
        Number key = jdbcInsert.executeAndReturnKey(param);
        entity.setId(key.longValue());
        return entity;
    }
}
