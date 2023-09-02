package com.flab.infrun.lecture.presentation.response;

import java.util.List;

public record PreSignedUrlResponse(
    String uploadId,
    List<String> preSignedUrlList
) {

}
