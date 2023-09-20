package com.flab.infrun.lecture.application.result;

import java.util.List;

public record PreSignedUrlResult(
    List<MultipartInfosResult> multipartInfosResults
) {

    public record MultipartInfosResult(
        String objectKey,
        String uploadId,
        List<String> preSignedUrlList) {

    }
}
