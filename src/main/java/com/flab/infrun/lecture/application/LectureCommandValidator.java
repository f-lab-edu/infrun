package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand.MultipartCommandInfo;
import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import org.springframework.stereotype.Component;

@Component
public class LectureCommandValidator {

    public void validateObjectPath(PublishPreSignedUrlCommand command) {
        long distinctCount = command.multipartInfos().stream().map(MultipartCommandInfo::objectPath)
            .distinct().count();
        if (command.multipartInfos().size() > distinctCount) {
            throw new DuplicateLectureFileNameException();
        }
    }
}
