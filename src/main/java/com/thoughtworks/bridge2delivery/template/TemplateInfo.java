package com.thoughtworks.bridge2delivery.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
    private FileType fileType;
}
