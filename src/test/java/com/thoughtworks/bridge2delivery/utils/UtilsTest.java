package com.thoughtworks.bridge2delivery.utils;


import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class UtilsTest {

    @Test
    public void testGetJsonFromUrl() {
        String json = Utils.getFromUrl("http://localhost:8081/v2/api-docs");
        Assert.notNull(json, "json can not be null");
    }
}