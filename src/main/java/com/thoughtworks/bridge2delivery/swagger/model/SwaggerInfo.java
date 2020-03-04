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
    @Template(description = "标题")
    private String title;
    @Template(description = "版本号")
    private String version;
    @Template(description = "描述")
    private String description;
    @Template(description = "标签列表")
    private List<PathTag> tagList;
    @Template(description = "数据结构")
    private List<ObjectInfo> models;
}
