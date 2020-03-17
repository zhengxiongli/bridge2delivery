package com.thoughtworks.bridge2delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private T data;

    public static ApiResponse<String> error(String message) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(data);
        return apiResponse;
    }

    public static ApiResponse<?> ok() {
        return ok(null);
    }
}
