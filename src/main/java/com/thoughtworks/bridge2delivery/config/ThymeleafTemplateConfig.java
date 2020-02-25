package com.thoughtworks.bridge2delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class ThymeleafTemplateConfig {

    @Bean(name = "stringTemplateEngine")
    public SpringTemplateEngine stringTemplateEngine() {
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
