package com.flab.infrun.lecture.presentation.response;

import com.flab.infrun.lecture.application.result.PreSignedUrlResult;
import java.util.List;

public record PreSignedUrlResponse(
    List<MultipartResponseInfo> multipartResponseInfos
) {

    public static PreSignedUrlResponse from(PreSignedUrlResult preSignedUrlResult) {
        return new PreSignedUrlResponse(preSignedUrlResult.multipartInfosResults().stream().map(
            result -> new MultipartResponseInfo(
                result.objectKey(),
                result.uploadId(),
                result.preSignedUrlList()
            )).toList()
        );
    }

    record MultipartResponseInfo(
        String objectKey,
        String uploadId,
        List<String> preSignedUrlList
    ) {

    }
}
