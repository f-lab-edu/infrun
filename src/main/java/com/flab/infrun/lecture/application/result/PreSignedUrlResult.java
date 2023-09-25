package com.flab.infrun.lecture.application.result;

import java.util.List;

public record PreSignedUrlResult(
    String uploadId,
    List<String> preSignedUrlList
) {

}
