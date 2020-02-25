package com.thoughtworks.bridge2delivery.swagger.model;

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
    private String name;
    private String description;
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
