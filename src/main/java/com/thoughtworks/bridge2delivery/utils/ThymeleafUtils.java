package com.thoughtworks.bridge2delivery.utils;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Component
public class ThymeleafUtils {
    private final SpringTemplateEngine stringTemplateEngine;
    private ThymeleafUtils(final SpringTemplateEngine stringTemplateEngine) {
        this.stringTemplateEngine = stringTemplateEngine;
    }

    public String renderTemplate(String template, Map<String, Object> variables) {
        Context ctx = new Context();
        ctx.setVariables(variables);
        return stringTemplateEngine.process(template, ctx);
    }
}
