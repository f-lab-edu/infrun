package com.flab.infrun.lecture.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class InvalidAuthorizationLectureReviewException extends SystemException {

    public InvalidAuthorizationLectureReviewException() {
        super(ErrorCode.INVALID_AUTHORIZATION_LECTURE_REVIEW);
    }
}
