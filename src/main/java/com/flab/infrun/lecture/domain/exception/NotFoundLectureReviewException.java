package com.flab.infrun.lecture.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class NotFoundLectureReviewException extends SystemException {

    public NotFoundLectureReviewException() {
        super(ErrorCode.NOT_FOUND_LECTURE_REVIEW);
    }
}
