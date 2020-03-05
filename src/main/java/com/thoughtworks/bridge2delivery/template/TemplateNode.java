package com.thoughtworks.bridge2delivery.template;

import lombok.Data;

import java.util.List;

@Data
public class TemplateNode {
    private String parentName;
    private String name;
    private String description;
    private Boolean isArray;
    private List<TemplateNode> childNodes;
}
