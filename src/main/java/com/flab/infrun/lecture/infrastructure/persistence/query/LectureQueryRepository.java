package com.flab.infrun.lecture.infrastructure.persistence.query;

import static com.flab.infrun.lecture.domain.QLecture.lecture;
import static com.flab.infrun.member.domain.QMember.member;

import com.flab.infrun.lecture.infrastructure.data.LectureDTO;
import com.flab.infrun.lecture.infrastructure.persistence.query.condition.LectureSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class LectureQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<LectureDTO> search(LectureSearchCondition condition) {
        return jpaQueryFactory
            .select(
                Projections.constructor(LectureDTO.class,
                    lecture.id,
                    lecture.name,
                    lecture.price,
                    lecture.lectureLevel,
                    lecture.skill,
                    lecture.member.id.as("uploadUserId"),
                    lecture.member.nickname.as("uploadUserName"))
            )
            .from(lecture)
            .leftJoin(lecture.member, member)
            .where(
                //todo-like 검색
                nameConditionEq(condition.name()),
                priceConditionEq(condition.price()),
                lectureLevelConditionEq(condition.lectureLevel()),
                skillConditionEq(condition.skill()),
                uploadUserNameConditionEq(condition.uploadUserName())
            )
            .fetch();
    }

    private BooleanExpression nameConditionEq(String name) {
        return StringUtils.hasText(name) ? lecture.name.like("%" + name + "%") : null;
    }

    private BooleanExpression priceConditionEq(Integer price) {
        return price != null ? lecture.price.eq(price) : null;
    }

    private BooleanExpression lectureLevelConditionEq(Integer lectureLevel) {
        return lectureLevel != null ? lecture.lectureLevel.eq(lectureLevel) : null;
    }

    private BooleanExpression skillConditionEq(String skill) {
        return StringUtils.hasText(skill) ? lecture.skill.eq(skill) : null;
    }

    private BooleanExpression uploadUserNameConditionEq(String uploadUserName) {
        return uploadUserName != null ? lecture.member.nickname.like("%" + uploadUserName + "%")
            : null;
    }
}
