package com.thoughtworks.bridge2delivery.utils;


import com.thoughtworks.bridge2delivery.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UtilsTest {

    @Test
    public void should_throw_exception_when_remote_not_existed() {
        Assertions.assertThrows(CustomException.class, () -> Utils.getFromUrl("http://localhost:8081/v2/api-docs"));
    }
}