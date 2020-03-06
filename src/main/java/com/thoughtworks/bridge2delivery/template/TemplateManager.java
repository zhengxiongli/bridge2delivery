package com.thoughtworks.bridge2delivery.template;

import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;

import java.util.HashMap;
import java.util.Map;

public final class TemplateManager {
    private static final Map<TemplateType, TemplateInfo> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put(TemplateType.SWAGGER, TemplateInfo.builder().templateType(TemplateType.SWAGGER)
                .templateNodes(TemplateUtils.getTemplateNodes(SwaggerInfo.class)).rootName("swaggerInfo")
                .rootDesc("Swagger属性列表").build());
    }

    private TemplateManager() {

    }

    public static TemplateInfo getTemplateInfo(TemplateType type) {
        if (type == null) {
            return null;
        }
        return TEMPLATES.get(type);
    }
}
