package com.flab.infrun.common.exception;

public enum ErrorCode {

    SYSTEM_ERROR("시스템 에러가 발생하였습니다.");

    public final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
