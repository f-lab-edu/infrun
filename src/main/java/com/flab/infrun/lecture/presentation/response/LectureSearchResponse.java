package com.flab.infrun.lecture.presentation.response;

import java.util.List;

public record LectureSearchResponse(
    List<LectureSearchListResponse> lectureSearchListResponses
) {

}
