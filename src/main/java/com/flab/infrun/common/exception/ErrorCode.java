package com.flab.infrun.common.exception;

public enum ErrorCode {

    // COMMON
    SYSTEM_ERROR("시스템 에러가 발생하였습니다."),
    INVALID_PARAMETER("요청값이 올바르지 않습니다."),

    // AUTH
    UN_AUTHORIZATION("유효하지 않은 인증정보입니다."),
    ACCESS_DENIED("접근 권한이 없습니다."),

    // MEMBER
    DUPLICATED_NICKNAME("이미 존재하는 닉네임입니다."),
    DUPLICATED_EMAIL("이미 존재하는 이메일입니다."),
    NOT_FOUND_MEMBER("존재하지 않는 회원입니다."),

    //LECTURE
    DUPLICATED_FILE_NAME("중복되는 파일명이 존재합니다."),
    NOT_FOUND_LECTURE("존재하지 않는 강의입니다."),

    //LECTURE_REVIEW
    NOT_FOUND_LECTURE_REVIEW("존재하지 않는 강의 리뷰입니다."),
    INVALID_AUTHORIZATION_LECTURE_REVIEW("리뷰 수정 권한이 없습니다."),

    // COUPON
    INVALID_COUPON_EXPIRATION_AT("쿠폰 만료일이 유효하지 않습니다."),
    INVALID_COUPON_QUANTITY("쿠폰 수량이 유효하지 않습니다."),
    INVALID_COUPON_DISCOUNT_TYPE("쿠폰 할인 타입이 유효하지 않습니다."),
    INVALID_COUPON_DISCOUNT_AMOUNT("쿠폰 할인 금액 또는 할인율이 유효하지 않습니다."),
    NOT_FOUND_COUPON("존재하지 않는 쿠폰입니다."),
    ALREADY_REGISTERED_COUPON("이미 등록된 쿠폰입니다."),
    EXPIRED_COUPON("만료된 쿠폰입니다."),
    UNAVAILABLE_COUPON("사용할 수 없는 쿠폰입니다."),

    // CART
    NOT_FOUND_CART("수강바구니를 찾을 수 없습니다."),
    NOT_FOUND_CART_ITEM("수강바구니에 해당 상품을 찾을 수 없습니다.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
