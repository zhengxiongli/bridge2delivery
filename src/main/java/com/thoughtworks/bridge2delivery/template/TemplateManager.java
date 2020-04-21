package com.thoughtworks.bridge2delivery.template;

import com.thoughtworks.bridge2delivery.cucumber.model.CucumberInfo;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;

import java.util.HashMap;
import java.util.Map;

public final class TemplateManager {
    private static final Map<TemplateType, TemplateInfo> TEMPLATES = new HashMap<>();

    static {
        TEMPLATES.put(TemplateType.SWAGGER, TemplateInfo.builder().templateType(TemplateType.SWAGGER)
                .fileType(FileType.WORD).templateNodes(TemplateUtils.getTemplateNodes(SwaggerInfo.class))
                .rootName("swaggerInfo").rootDesc("Swagger属性列表").defaultUrl("/template/swagger.html")
                .filePrefix("Swagger转doc模板_").build());
        TEMPLATES.put(TemplateType.CUCUMBER, TemplateInfo.builder().templateType(TemplateType.CUCUMBER)
                .fileType(FileType.EXCEL).templateNodes(TemplateUtils.getTemplateNodes(CucumberInfo.class))
                .rootName("cucumberInfo").rootDesc("Cucumber属性列表").defaultUrl("/template/cucumber.html")
                .filePrefix("Cucumber转Excel模板_").build());
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
