package com.thoughtworks.bridge2delivery.template;

import lombok.Data;

import java.util.List;

@Data
public class TemplateNode {
    private String name;
    private String description;
    private Boolean isArray;
    private NodeType nodeType;
    private List<TemplateNode> childNodes;
}
