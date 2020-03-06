package com.thoughtworks.bridge2delivery.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateInfo {
    private String previewUrl;
    private String defaultUrl;
    private String rootName;
    private String rootDesc;
    private List<TemplateNode> templateNodes;
    private TemplateType templateType;
}
