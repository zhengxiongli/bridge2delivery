package com.thoughtworks.bridge2delivery.exception;

import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandlerUnderTest;

    @BeforeEach
    public void setUp() {
        globalExceptionHandlerUnderTest = new GlobalExceptionHandler();
    }

    @Test
    public void should_handle_custom_exception() {
        // given
        final CustomException exception = new CustomException("message");
        final ApiResponse<String> stringApiResponse = new ApiResponse<>();
        stringApiResponse.setMessage("message");
        stringApiResponse.setData(null);
        final ResponseEntity<ApiResponse<String>> expectedResult =
                new ResponseEntity<>(stringApiResponse, HttpStatus.FORBIDDEN);

        // when
        final ResponseEntity<ApiResponse<String>> result = globalExceptionHandlerUnderTest.handleCustomException(exception);

        // then
        assertEquals(expectedResult, result);
    }

    @Test
    public void should_handle_throwable() {
        // given
        final Throwable e = new Throwable("message");
        final ApiResponse<String> stringApiResponse = new ApiResponse<>();
        stringApiResponse.setMessage("message");
        stringApiResponse.setData(null);
        final ResponseEntity<ApiResponse<String>> expectedResult =
                new ResponseEntity<>(stringApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        // when
        final ResponseEntity<ApiResponse<String>> result = globalExceptionHandlerUnderTest.handleException(e);

        // then
        assertEquals(expectedResult, result);
    }
}
