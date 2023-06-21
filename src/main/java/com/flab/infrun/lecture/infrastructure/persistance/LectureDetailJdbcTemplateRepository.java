package com.flab.infrun.lecture.infrastructure.persistance;

import com.flab.infrun.lecture.domain.LectureDetail;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class LectureDetailJdbcTemplateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public LectureDetailJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("lecture_detail")
            .usingGeneratedKeyColumns("id");
    }

    public Optional<LectureDetail> findById(Long id) {
        String sql = "select id , chapter, name, lecture_id, file_id FROM LECTURE_DETAIL where id = :id";
        Map<String, Object> param = Map.of("id", id);
        LectureDetail lectureDetail = jdbcTemplate.queryForObject(sql, param, itemRowMapper());

        return Optional.ofNullable(lectureDetail);
    }

    private RowMapper<LectureDetail> itemRowMapper() {
        return (rs, rowNum) -> {
            LectureDetail lecture = LectureDetail.of(
                rs.getString("chapter"),
                rs.getString("name"),
                rs.getLong("lecture_id"),
                rs.getLong("file_id")
            );
            lecture.setId(rs.getLong("id"));
            return lecture;
        };
    }

    public LectureDetail save(LectureDetail entity) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(
            entity);
        Number key = jdbcInsert.executeAndReturnKey(param);
        entity.setId(key.longValue());

        return entity;
    }
}
