package com.thoughtworks.bridge2delivery.template;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Template {
    String value() default "";

    @AliasFor("value")
    String description() default "";

    //数字越小越靠前
    int order() default 0;
}
