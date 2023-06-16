package com.flab.infrun.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flab.infrun.common.exception.ErrorCode;

public record Response<T>(
    boolean success,
    @JsonInclude(Include.NON_NULL)
    T data,
    @JsonInclude(Include.NON_NULL)
    ErrorCode errorCode,
    @JsonInclude(Include.NON_NULL)
    String message) {

    public static <T> Response<T> success(final T data) {
        return new Response<>(true, data, null, null);
    }

    public static <T> Response<T> fail(final ErrorCode errorCode) {
        return new Response<>(false, null, errorCode, null);
    }

    public static <T> Response<T> fail(final ErrorCode errorCode, final String message) {
        return new Response<>(false, null, errorCode, message);
    }
}
