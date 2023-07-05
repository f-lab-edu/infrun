package com.flab.infrun.lecture.application.exception;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.exception.SystemException;

public class DuplicateLectureFileNameException extends SystemException {

    public DuplicateLectureFileNameException() {
        super(ErrorCode.DUPLICATED_FILE_NAME);
    }
}
