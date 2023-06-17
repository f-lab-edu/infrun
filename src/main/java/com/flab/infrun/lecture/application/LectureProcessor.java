package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.infrastructure.persistance.LectureRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureProcessor {

    private final LectureRepositoryAdapter lectureRepositoryAdapter;

    public Long registerLecture(LectureRegisterCommand lectureRegisterCommand) {
        Lecture savedLecture = lectureRepositoryAdapter.save(Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.introduce()));
        return savedLecture.getId();
    }
}
