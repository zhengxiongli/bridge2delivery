package com.thoughtworks.bridge2delivery.swagger.model;

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
    private String title;
    private String version;
    private String description;
    private List<PathTag> tagList;
    private List<ObjectInfo> models;
}
