package com.thoughtworks.bridge2delivery.exception;

import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = CustomException.class)
    @Order(1)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException exception) {
        log.error("custom error", exception);
        return new ResponseEntity<ApiResponse>(ApiResponse.error(exception.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Throwable.class)
    @Order(2)
    public ResponseEntity<ApiResponse> handleException(Throwable e) {
        log.error("exception", e);
        return new ResponseEntity<ApiResponse>(ApiResponse.error(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
