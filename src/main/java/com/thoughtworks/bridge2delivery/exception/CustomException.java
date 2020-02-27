package com.thoughtworks.bridge2delivery.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
    }
}
