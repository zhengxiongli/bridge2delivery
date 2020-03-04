package com.thoughtworks.bridge2delivery.template;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class TemplateUtils {
    private TemplateUtils() {

    }

    public static List<TemplateNode> getTemplateNodes(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return Collections.emptyList();
        }
        List<TemplateNode> templateNodes = getChildTemplateNodes(clazz);
        return templateNodes;
    }

    private static List<TemplateNode> getChildTemplateNodes(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return null;
        }
        List<TemplateNode> templateNodes = new ArrayList<>();
        for (Field field : fields) {
            Template template = field.getAnnotation(Template.class);
            if (template == null) {
                continue;
            }
            TemplateNode node = new TemplateNode();
            node.setIsArray(Collection.class.isAssignableFrom(field.getType()));
            node.setName(field.getName());
            node.setDescription(template.description());
            node.setChildNodes(getChildTemplateNodes(node.getIsArray() ? getArrayClassType(field) : field.getType()));
            templateNodes.add(node);
        }
        if (templateNodes == null) {
            return null;
        }
        return templateNodes.size() == 0 ? null : templateNodes;
    }

    private static Class<?> getArrayClassType(Field field) {
        Object o = field.getGenericType();
        try {
            Field field1 = o.getClass().getDeclaredField("actualTypeArguments");
            field1.setAccessible(true);
            Type[] type = (Type[]) field1.get(o);
            return (Class<?>) type[0];
        } catch (Exception e) {
            return field.getType();
        }
    }
}
