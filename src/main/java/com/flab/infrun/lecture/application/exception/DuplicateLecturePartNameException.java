package com.flab.infrun.lecture.application.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class DuplicateLecturePartNameException extends SystemException {

    public DuplicateLecturePartNameException() {
        super(ErrorCode.DUPLICATED_LECTURE_PART_NAME);
    }
}
