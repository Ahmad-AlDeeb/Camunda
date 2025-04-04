package com.deeb.hiringprocess.entity;

public record ApiResponse<T>(int statusCode, String message, T body) {
}
