package com.thoughtworks.bridge2delivery.template;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class TemplateUtils {
    private TemplateUtils() {

    }

    public static List<TemplateNode> getTemplateNodes(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return Collections.emptyList();
        }
        return getChildTemplateNodes(clazz);
    }

    private static List<TemplateNode> getChildTemplateNodes(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
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
            node.setOrder(template.order());
            node.setChildNodes(getChildTemplateNodes(node.getIsArray() ? getArrayClassType(field) : field.getType()));
            if (node.getIsArray()) {
                if (node.getChildNodes() != null) {
                    node.getChildNodes().add(0, getArrayIndexNode());
                } else {
                    node.setChildNodes(Arrays.asList(getArrayIndexNode()));
                }
            }
            templateNodes.add(node);
        }
        return templateNodes.size() == 0 ? null : templateNodes.stream()
                .sorted(Comparator.comparing(TemplateNode::getOrder)).collect(Collectors.toList());
    }

    private static TemplateNode getArrayIndexNode() {
        TemplateNode node = new TemplateNode();
        node.setIsArray(false);
        node.setName("arrayIndex");
        node.setDescription("序号");
        node.setOrder(-1);
        node.setNodeType(NodeType.ARRAY_INDEX);
        return node;
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
