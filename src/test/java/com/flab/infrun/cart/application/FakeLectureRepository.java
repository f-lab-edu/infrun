package com.flab.infrun.cart.application;

import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FakeLectureRepository implements LectureRepository {

    private final Map<Long, Lecture> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Lecture save(final Lecture lecture) {
        persistence.put(++sequence, lecture);
        lecture.assignId(sequence);
        return lecture;
    }

    public void saveAll(final List<Lecture> lectures) {
        for (Lecture lecture : lectures) {
            persistence.put(++sequence, lecture);
            lecture.assignId(sequence);
        }
    }

    @Override
    public Optional<Lecture> findById(final Long id) {
        return persistence.values().stream()
            .filter(lecture -> lecture.getId().equals(id))
            .findFirst();
    }
}
