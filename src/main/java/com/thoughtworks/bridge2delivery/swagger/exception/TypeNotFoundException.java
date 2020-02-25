package com.thoughtworks.bridge2delivery.swagger.exception;

public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException() {
        super("data type not found");
    }
}
