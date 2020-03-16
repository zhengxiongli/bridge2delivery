package com.thoughtworks.bridge2delivery.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    @Disabled
    public void should_get_json_from_remote_url() {
        String json = Utils.getFromUrl("http://localhost:8081/v2/api-docs");
        Assertions.assertNotNull(json);
    }
}