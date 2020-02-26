package com.thoughtworks.bridge2delivery.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;

    public static ApiResponse error(String message) {
        ApiResponse response = new ApiResponse();
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse ok(T data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(data);
        return apiResponse;
    }
}
