package com.thoughtworks.bridge2delivery.utils;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

@Component
public class ThymeleafUtils {
    private final SpringTemplateEngine stringTemplateEngine;
    private ThymeleafUtils() {
        this.stringTemplateEngine = getStringTemplateEngine();
    }

    public String renderTemplate(String template, Map<String, Object> variables) {
        Context ctx = new Context();
        ctx.setVariables(variables);
        return stringTemplateEngine.process(template, ctx);
    }

    private static SpringTemplateEngine getStringTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        IDialect iDialect = new SpringStandardDialect();
        springTemplateEngine.setDialect(iDialect);

        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setCacheable(true);
        stringTemplateResolver.setTemplateMode(TemplateMode.HTML);
        springTemplateEngine.setTemplateResolver(stringTemplateResolver);
        return springTemplateEngine;
    }
}
