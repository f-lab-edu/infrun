package com.flab.infrun.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flab.infrun.common.exception.ErrorCode;

public record Response<T>(
    boolean success,
    T data,
    @JsonInclude(Include.NON_NULL)
    ErrorCode errorCode
) {

    public static <T> Response<T> success(T data) {
        return new Response<>(true, data, null);
    }

    public static <T> Response<T> fail(T data, ErrorCode errorCode) {
        return new Response<>(false, data, errorCode);
    }
}
