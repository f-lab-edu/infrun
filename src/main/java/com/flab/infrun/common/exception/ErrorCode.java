package com.flab.infrun.common.exception;

public enum ErrorCode {

    // COMMON
    SYSTEM_ERROR("시스템 에러가 발생하였습니다."),
    INVALID_PARAMETER("요청값이 올바르지 않습니다."),

    // MEMBER
    DUPLICATED_NICKNAME("이미 존재하는 닉네임입니다."),
    DUPLICATED_EMAIL("이미 존재하는 이메일입니다."),
    INVALID_AUTHENTICATION("유효하지 않은 인증정보입니다."),
    NOT_FOUND_MEMBER("존재하지 않는 회원입니다.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
