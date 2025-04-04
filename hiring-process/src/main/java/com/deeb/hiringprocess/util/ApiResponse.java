package com.deeb.hiringprocess.util;

public record ApiResponse<T>(int statusCode, String message, T body) {
}
