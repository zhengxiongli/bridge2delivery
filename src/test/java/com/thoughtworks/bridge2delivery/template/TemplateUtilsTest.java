package com.thoughtworks.bridge2delivery.template;

import com.thoughtworks.bridge2delivery.swagger.model.ObjectInfo;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateUtilsTest {
    @Test
    public void getTemplateNodesTest() {
        TemplateUtils.getTemplateNodes(ObjectInfo.class);
    }
}