package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.template.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PathTag {
    @Template(description = "名称", order = 0)
    private String name;
    @Template(description = "描述", order = 1)
    private String description;
    @Template(description = "路径", order = 2)
    private List<PathInfo> pathList;

    public void addPath(PathInfo pathInfo) {
        if (pathInfo == null) {
            return;
        }
        if (pathList == null) {
            pathList = new ArrayList<>();
        }
        pathList.add(pathInfo);
    }
}
