package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.command.LectureReviewDeleteCommand;
import com.flab.infrun.lecture.application.command.LectureReviewModifyCommand;
import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand.MultipartCommandInfo;
import com.flab.infrun.lecture.application.result.PreSignedUrlResult;
import com.flab.infrun.lecture.application.result.PreSignedUrlResult.MultipartInfosResult;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureReview;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureException;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureReviewException;
import com.flab.infrun.lecture.domain.repository.LectureRepository;
import com.flab.infrun.lecture.domain.repository.LectureReviewRepository;
import com.flab.infrun.lecture.infrastructure.storage.aws.SimpleStorageService;
import com.flab.infrun.lecture.infrastructure.storage.aws.config.S3Config;
import com.flab.infrun.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class LectureCommandProcessor {

    private final LectureRepository lectureRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final SimpleStorageService s3;
    private final S3Config s3Config;
    private final LectureCommandValidator lectureCommandValidator;

    public PreSignedUrlResult publishPreSignedUrl(PublishPreSignedUrlCommand command) {
        lectureCommandValidator.validatePathNameUrl(command);

        List<MultipartInfosResult> multipartInfosResults = new ArrayList<>();

        for (MultipartCommandInfo info : command.multipartInfos()) {
            String objectKey = command.memberId() + "/" + info.objectPath();
            String uploadId = s3.getUploadId(s3Config.getBucketName(), objectKey,
                s3Config.getProfileName(), s3Config.getRegion());
            List<String> preSignedList = s3.getPreSignedUrl(s3Config.getBucketName(),
                objectKey, s3Config.getProfileName(), s3Config.getRegion(), uploadId,
                info.partCnt(), s3Config.getDuration());

            multipartInfosResults.add(new MultipartInfosResult(objectKey, uploadId, preSignedList));
        }

        return new PreSignedUrlResult(multipartInfosResults);
    }

    @Transactional
    public Long registerLecture(LectureRegisterCommand lectureRegisterCommand, Member member) {
        lectureCommandValidator.validatePathNameRegister(lectureRegisterCommand);

        completeMultipartUpload(lectureRegisterCommand);

        return getSavedLecture(lectureRegisterCommand, member);
    }

    private Long getSavedLecture(LectureRegisterCommand lectureRegisterCommand, Member member) {
        Lecture lecture = Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.level(),
            lectureRegisterCommand.skill(),
            lectureRegisterCommand.introduce(),
            member);

        List<LectureDetail> lectureDetails = lectureRegisterCommand.lectureDetailCommandList()
            .stream()
            .map(detail -> LectureDetail.of(detail.chapter(), detail.name(), detail.objectKey()))
            .toList();
        lecture.addLectureDetail(lectureDetails);

        return lectureRepository.save(lecture).getId();
    }

    private void completeMultipartUpload(LectureRegisterCommand lectureRegisterCommand) {
        lectureRegisterCommand.lectureDetailCommandList().stream()
            .filter(lectureDetail -> null != lectureDetail.objectKey() && !"".equals(
                lectureDetail.objectKey()))
            .forEach(
                lectureDetail -> s3.completeUpload(s3Config.getProfileName(),
                    lectureDetail.objectKey(), s3Config.getProfileName(), s3Config.getRegion(),
                    lectureDetail.uploadId(), lectureDetail.etagList()));
    }

    public Long registerLectureReview(LectureReviewRegisterCommand command, Member member) {
        Lecture lecture = lectureRepository.findById(command.lectureId())
            .orElseThrow(NotFoundLectureException::new);
        LectureReview lectureReview = LectureReview.of(command.content(), lecture, member);
        LectureReview saved = lectureReviewRepository.save(lectureReview);
        return saved.getId();
    }

    @Transactional
    public Long modifyLectureReview(LectureReviewModifyCommand command, Member member) {
        LectureReview lectureReview = lectureReviewRepository.findById(command.lectureReviewId())
            .orElseThrow(NotFoundLectureReviewException::new);
        lectureReview.checkReviewAuthorization(member);
        lectureReview.changeContent(command.content());
        return lectureReview.getId();
    }

    @Transactional
    public Long deleteLectureReview(LectureReviewDeleteCommand command, Member member) {
        LectureReview lectureReview = lectureReviewRepository.findById(command.lectureReviewId())
            .orElseThrow(NotFoundLectureReviewException::new);
        lectureReview.checkReviewAuthorization(member);
        return lectureReviewRepository.deleteById(lectureReview.getId());
    }
}
