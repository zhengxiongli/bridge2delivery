package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.template.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerInfo {
    @Template(description = "标题", order = 0)
    private String title;
    @Template(description = "版本号", order = 1)
    private String version;
    @Template(description = "描述", order = 2)
    private String description;
    @Template(description = "标签列表", order = 3)
    private List<PathTag> tagList;
    @Template(description = "数据结构", order = 4)
    private List<ObjectInfo> models;
}
