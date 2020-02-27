package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
public class PathInfo {
    private String path;
    private String method;
    private String summary;
    private String description;
    private List<String> tagNames;
    private String produces;
    private boolean deprecated;
    private List<ParamInfo> params;
    private List<Response> responses;

    public PathInfo fillInfo(Map<String, Map> pathInfoMap, Map<String, BaseInfo> models) {
        this.tagNames = (List<String>) pathInfoMap.get("tags");
        this.setSummary(JSONUtils.getMapValueAndToString(pathInfoMap, "summary"));
        this.setDescription(JSONUtils.getMapValueAndToString(pathInfoMap, "description"));
        this.setDeprecated(Boolean.parseBoolean(JSONUtils.getMapValueAndToString(pathInfoMap, "deprecated")));
        this.setProduces(JSONUtils.getMapValueAndToString(pathInfoMap, "produces"));
        buildParams((List<Map>) pathInfoMap.get("parameters"), models);
        buildResponses(pathInfoMap.get("responses"), models);
        return this;
    }

    public String getSummary() {
        return StringUtils.isEmpty(summary) ? pathToSummary() : summary;
    }

    private String pathToSummary() {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        String summary = path.replaceAll("/", " ");
        return summary.trim();
    }

    private void buildParams(List<Map> parameters, Map<String, BaseInfo> models) {
        if (CollectionUtils.isEmpty(parameters)) {
            return;
        }
        List<ParamInfo> paramInfos = new ArrayList<>(parameters.size());
        for (Map<String, Map> map : parameters) {
            ParamInfo paramInfo = new ParamInfo();
            paramInfo.build(map, models);
            paramInfos.add(paramInfo);
        }
        this.setParams(paramInfos);
    }

    private void buildResponses(Map<String, Map> resMap, Map<String, BaseInfo> models) {
        if (CollectionUtils.isEmpty(resMap)) {
            return;
        }
        List<Response> responses = new ArrayList<>();
        Iterator<Map.Entry<String, Map>> iterator = resMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map> entry = iterator.next();
            Response res = new Response();
            res.setStatus(entry.getKey());
            res.build(entry.getValue(), models);
            responses.add(res);
        }
        this.setResponses(responses);
    }
}
