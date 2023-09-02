package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.command.LectureReviewDeleteCommand;
import com.flab.infrun.lecture.application.command.LectureReviewModifyCommand;
import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureReview;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureException;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureReviewException;
import com.flab.infrun.lecture.domain.repository.LectureRepository;
import com.flab.infrun.lecture.domain.repository.LectureReviewRepository;
import com.flab.infrun.lecture.infrastructure.storage.aws.SimpleStorageService;
import com.flab.infrun.lecture.presentation.response.PreSignedUrlResponse;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class LectureCommandProcessor {

    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final SimpleStorageService s3;

    public PreSignedUrlResponse publishPreSignedUrl(PublishPreSignedUrlCommand command) {
        String uploadId = s3.getUploadIdDefaultConfig(command.objectKey());
        List<String> preSignedList = s3.getPreSignedUrlDefaultConfig(command.objectKey(), uploadId,
            command.partCnt());
        return new PreSignedUrlResponse(uploadId, preSignedList);
    }

    @Transactional
    public Long registerLecture(LectureRegisterCommand lectureRegisterCommand) {
        completeMultipartUpload(lectureRegisterCommand);
        return getSavedLecture(lectureRegisterCommand);
    }

    private Long getSavedLecture(LectureRegisterCommand lectureRegisterCommand) {
        Member member = memberRepository.findById(lectureRegisterCommand.memberId());
        Lecture lecture = Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.level(),
            lectureRegisterCommand.skill(),
            lectureRegisterCommand.introduce(),
            member);

        List<LectureDetail> lectureDetails = lectureRegisterCommand.lectureDetailCommandList()
            .stream()
            .map(d -> LectureDetail.of(d.chapter(), d.name(), d.objectKey()))
            .toList();
        lecture.addLectureDetail(lectureDetails);
        return lectureRepository.save(lecture).getId();
    }

    private void completeMultipartUpload(LectureRegisterCommand lectureRegisterCommand) {
        lectureRegisterCommand.lectureDetailCommandList().stream()
            .filter(t -> null != t.objectKey() && !"".equals(t.objectKey()))
            .forEach(
                d -> s3.completeUploadDefaultConfig(d.objectKey(), d.uploadId(), d.etagList()));
    }

    public Long registerLectureReview(LectureReviewRegisterCommand command) {
        Lecture lecture = lectureRepository.findById(command.lectureId())
            .orElseThrow(NotFoundLectureException::new);
        Member member = memberRepository.findByEmail(command.memberEmail()).orElseThrow(
            NotFoundMemberException::new);
        LectureReview lectureReview = LectureReview.of(command.content(), lecture, member);
        LectureReview saved = lectureReviewRepository.save(lectureReview);
        return saved.getId();
    }

    @Transactional
    public Long modifyLectureReview(LectureReviewModifyCommand command) {
        LectureReview lectureReview = lectureReviewRepository.findById(command.lectureReviewId())
            .orElseThrow(NotFoundLectureReviewException::new);
        Member member = memberRepository.findByEmail(command.memberEmail()).orElseThrow(
            NotFoundMemberException::new);
        lectureReview.checkReviewAuthorization(member);
        lectureReview.changeContent(command.content());
        return lectureReview.getId();
    }

    @Transactional
    public Long deleteLectureReview(LectureReviewDeleteCommand command) {
        LectureReview lectureReview = lectureReviewRepository.findById(command.lectureReviewId())
            .orElseThrow(NotFoundLectureReviewException::new);
        Member member = memberRepository.findByEmail(command.memberEmail()).orElseThrow(
            NotFoundMemberException::new);
        lectureReview.checkReviewAuthorization(member);
        return lectureReviewRepository.deleteById(lectureReview.getId());
    }
}
