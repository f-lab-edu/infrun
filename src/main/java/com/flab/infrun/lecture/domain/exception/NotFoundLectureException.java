package com.flab.infrun.lecture.domain.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class NotFoundLectureException extends SystemException {

    public NotFoundLectureException() {
        super(ErrorCode.NOT_FOUND_LECTURE);
    }
}
