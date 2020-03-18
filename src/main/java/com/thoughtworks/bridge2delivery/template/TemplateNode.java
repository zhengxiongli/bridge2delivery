package com.thoughtworks.bridge2delivery.template;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TemplateNode {
    private String name;
    private String description;
    private Boolean isArray;
    private NodeType nodeType;
    private List<TemplateNode> childNodes;
    private Integer order;
}
