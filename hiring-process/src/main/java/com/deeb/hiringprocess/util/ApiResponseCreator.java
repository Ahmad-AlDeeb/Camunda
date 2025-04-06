package com.deeb.hiringprocess.util;

import static com.deeb.hiringprocess.constant.ExceptionConstant.UTILITY_CLASS_INSTANTIATION_MESSAGE;

public final class ApiResponseCreator {

    public static <T> ApiResponse<T> createUnifiedResponse(int statusCode, String message, T body) {
        return new ApiResponse<>(statusCode, message, body);
    }

    private ApiResponseCreator() {
        throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
