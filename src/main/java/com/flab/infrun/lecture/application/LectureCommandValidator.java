package com.flab.infrun.lecture.application;

import com.flab.infrun.common.exception.SystemException;
import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.exception.DuplicateLecturePartNameException;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class LectureCommandValidator {

    public void validatePathNameRegister(LectureRegisterCommand command) {
        validateDup(command.lectureDetailCommandList(),
            detail -> detail.chapter() + detail.name(),
            new DuplicateLecturePartNameException());
    }

    public void validatePathNameUrl(PublishPreSignedUrlCommand command) {
        validateDup(command.multipartInfos(),
            detail -> detail.objectPath().substring(detail.objectPath().lastIndexOf("/")),
            new DuplicateLecturePartNameException());
    }

    private <T, R> void validateDup(List<T> list, Function<T, R> function,
        SystemException systemException) {
        long distinctCnt = list.stream()
            .map(function)
            .distinct().count();
        if (list.size() > distinctCnt) {
            throw systemException;
        }
    }
}
