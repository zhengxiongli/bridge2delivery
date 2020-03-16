package com.thoughtworks.bridge2delivery.template;

import com.thoughtworks.bridge2delivery.swagger.model.ObjectInfo;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TemplateUtilsTest {

    @Test
    public void should_get_template_info() {
        List<TemplateNode> templateNodes = TemplateUtils.getTemplateNodes(ObjectInfo.class);

        assertNotNull(templateNodes);
    }
}