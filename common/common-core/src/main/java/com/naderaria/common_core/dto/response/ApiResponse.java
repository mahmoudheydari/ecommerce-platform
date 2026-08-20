package com.naderaria.common_core.dto.response;

public record ApiResponse<T>(String messageCode, T data) {

    public static <T> ApiResponse<T> of(String messageCode, T data) {
        return new ApiResponse<>(messageCode, data);
    }

    public static ApiResponse<Void> of(String messageCode) {
        return new ApiResponse<>(messageCode,null);
    }
}
