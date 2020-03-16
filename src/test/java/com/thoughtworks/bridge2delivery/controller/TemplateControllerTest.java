package com.thoughtworks.bridge2delivery.controller;

import com.thoughtworks.bridge2delivery.dto.ApiResponse;
import com.thoughtworks.bridge2delivery.template.TemplateInfo;
import com.thoughtworks.bridge2delivery.template.TemplateType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class TemplateControllerTest {

    @Autowired
    private TemplateController templateControllerUnderTest;

    @Test
    public void should_get_swagger_template_info_successful() {
        // given

        // when
        final ApiResponse<TemplateInfo> result = templateControllerUnderTest.getConfig(TemplateType.SWAGGER);

        // then
        assertEquals("swaggerInfo", result.getData().getRootName());
    }
}
