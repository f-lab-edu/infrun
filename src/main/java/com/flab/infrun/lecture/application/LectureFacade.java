package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.command.LectureReviewDeleteCommand;
import com.flab.infrun.lecture.application.command.LectureReviewModifyCommand;
import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.query.LectureSearchQuery;
import com.flab.infrun.lecture.application.result.LectureSearchResult;
import com.flab.infrun.lecture.application.result.PreSignedUrlResult;
import com.flab.infrun.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureFacade {

    private final LectureCommandProcessor lectureCommandProcessor;
    private final LectureQueryProcessor lectureQueryProcessor;

    public PreSignedUrlResult publishPreSignedUrl(PublishPreSignedUrlCommand command) {
        return lectureCommandProcessor.publishPreSignedUrl(command);
    }

    public long registerLecture(LectureRegisterCommand command, Member member) {
        return lectureCommandProcessor.registerLecture(command, member);
    }

    public LectureSearchResult searchLecture(LectureSearchQuery query) {
        return lectureQueryProcessor.searchLecture(query);
    }

    public Long registerLectureReview(LectureReviewRegisterCommand command, Member member) {
        return lectureCommandProcessor.registerLectureReview(command, member);
    }

    public Long modifyLectureReview(LectureReviewModifyCommand command, Member member) {
        return lectureCommandProcessor.modifyLectureReview(command, member);
    }

    public Long deleteLectureReview(LectureReviewDeleteCommand command, Member member) {
        return lectureCommandProcessor.deleteLectureReview(command, member);
    }
}
