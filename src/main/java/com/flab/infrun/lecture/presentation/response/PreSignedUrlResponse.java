package com.flab.infrun.lecture.presentation.response;

import com.flab.infrun.lecture.application.result.PreSignedUrlResult;
import java.util.List;

public record PreSignedUrlResponse(
    String uploadId,
    List<String> preSignedUrlList
) {

    public static PreSignedUrlResponse from(PreSignedUrlResult preSignedUrlResult) {
        return new PreSignedUrlResponse(preSignedUrlResult.uploadId(),
            preSignedUrlResult.preSignedUrlList());
    }
}
