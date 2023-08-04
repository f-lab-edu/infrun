package com.flab.infrun.lecture.presentation;

import com.flab.infrun.common.config.security.CurrentUser;
import com.flab.infrun.common.response.Response;
import com.flab.infrun.lecture.application.LectureFacade;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import com.flab.infrun.lecture.presentation.request.LectureReviewDeleteRequest;
import com.flab.infrun.lecture.presentation.request.LectureReviewModifyRequest;
import com.flab.infrun.lecture.presentation.request.LectureReviewRegisterRequest;
import com.flab.infrun.lecture.presentation.request.LectureSearchRequest;
import com.flab.infrun.lecture.presentation.response.LectureQueryResponse;
import com.flab.infrun.member.domain.Member;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Long> registerLecture(
        @Valid @RequestPart("lecture") LectureRegisterRequest lecture,
        @RequestPart("file") List<MultipartFile> lectureVideoFile,
        @CurrentUser Member member
    ) {
        var result = lectureFacade.registerLecture(
            lecture.toCommand(lectureVideoFile, member.getId()));
        return Response.success(result);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<List<LectureQueryResponse>> searchLecture(
        @Valid LectureSearchRequest lectureSearch
    ) {
        var result = lectureFacade.searchLecture(lectureSearch.toQuery());
        return Response.success(result);
    }

    @PostMapping("/review")
    public ResponseEntity<Response<Long>> registerLectureReview(
        @RequestBody @Valid LectureReviewRegisterRequest lectureReviewRequest) {
        var result = lectureFacade.registerLectureReview(lectureReviewRequest.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(result));
    }

    @PatchMapping("/review")
    public ResponseEntity<Response<Long>> modifyLectureReview(
        @RequestBody @Valid LectureReviewModifyRequest lectureReviewModifyRequest) {
        var result = lectureFacade.modifyLectureReview(lectureReviewModifyRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(result));
    }

    @DeleteMapping("/review")
    public ResponseEntity<Response<Long>> deleteLectureReview(
        @RequestBody @Valid LectureReviewDeleteRequest lectureReviewDeleteRequest) {
        var result = lectureFacade.deleteLectureReview(lectureReviewDeleteRequest.toCommand());
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(result));
    }
}

