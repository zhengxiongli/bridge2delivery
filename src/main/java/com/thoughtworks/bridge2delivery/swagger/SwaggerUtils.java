package com.thoughtworks.bridge2delivery.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.bridge2delivery.contents.Messages;
import com.thoughtworks.bridge2delivery.exception.CustomException;
import com.thoughtworks.bridge2delivery.swagger.model.BaseInfo;
import com.thoughtworks.bridge2delivery.swagger.model.ObjectInfo;
import com.thoughtworks.bridge2delivery.swagger.model.PathInfo;
import com.thoughtworks.bridge2delivery.swagger.model.PathTag;
import com.thoughtworks.bridge2delivery.swagger.model.SwaggerInfo;
import com.thoughtworks.bridge2delivery.utils.JSONUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SwaggerUtils {
    private static final String[] CHECK_ITEMS = {"paths", "definitions", "swagger", "info", "tags"};

    private SwaggerUtils() {

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static SwaggerInfo parseSwaggerJson(String swaggerJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(swaggerJson, new TypeReference<Map<String, Object>>() {
        });
        validate(map);
        SwaggerInfo swaggerInfo = new SwaggerInfo();
        Map<String, Map> info = (Map<String, Map>) map.get("info");
        swaggerInfo.setTitle(JSONUtils.getMapValueAndToString(info, "title"));
        swaggerInfo.setDescription(JSONUtils.getMapValueAndToString(info, "description"));
        swaggerInfo.setVersion(JSONUtils.getMapValueAndToString(info, "version"));
        swaggerInfo.setModels(parseModels((Map<String, Map>) map.get("definitions")));
        swaggerInfo.setTagList(parsePathTag((Map<String, Map>) map.get("paths"), (List) map.get("tags"),
                swaggerInfo.getModels().stream().collect(Collectors.toMap(ObjectInfo::getTitle, i -> i))));
        return swaggerInfo;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<PathTag> parsePathTag(Map<String, Map> pathMap, List<Map> tagList,
                                              Map<String, BaseInfo> models) {
        List<PathTag> pathTags = parseTagList(tagList);
        Map<String, PathTag> tagMap = pathTags.stream().collect(Collectors.toMap(PathTag::getName, tag -> tag));
        for (Map.Entry<String, Map> entry : pathMap.entrySet()) {
            Map<String, Map> methodMap = entry.getValue();
            for (Map.Entry<String, Map> mEntry : methodMap.entrySet()) {
                PathInfo pathInfo = new PathInfo();
                pathInfo.setPath(entry.getKey());
                pathInfo.setMethod(mEntry.getKey());
                pathInfo.fillInfo(mEntry.getValue(), models);
                addPathToTag(tagMap, pathInfo);
            }
        }
        return pathTags;
    }

    private static void addPathToTag(Map<String, PathTag> pathTagMap, PathInfo pathInfo) {
        List<String> tagNames = pathInfo.getTagNames();
        for (String tagName : tagNames) {
            PathTag tag = pathTagMap.get(tagName);
            if (tag == null) {
                continue;
            }
            tag.addPath(pathInfo);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<PathTag> parseTagList(List<Map> tagList) {
        List<PathTag> tags = new ArrayList<>(tagList.size());
        for (Map<String, String> map : tagList) {
            tags.add(PathTag.builder().name(map.get("name")).description(map.get("description")).build());
        }
        return tags;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<ObjectInfo> parseModels(Map<String, Map> definitions) {
        List<ObjectInfo> modelInfos = new ArrayList<>(definitions.size());
        for (Map.Entry<String, Map> entry : definitions.entrySet()) {
            modelInfos.add(parseModel(entry.getKey(), entry.getValue()));
        }
        fillRefModels(modelInfos);
        return modelInfos;
    }

    @SuppressWarnings("rawtypes")
    private static ObjectInfo parseModel(String title, Map<String, Map> modelInfo) {
        ObjectInfo model = new ObjectInfo();
        model.build(modelInfo);
        if (StringUtils.isEmpty(model.getTitle())) {
            model.setTitle(title);
        }
        return model;
    }

    private static void fillRefModels(List<ObjectInfo> models) {
        if (CollectionUtils.isEmpty(models)) {
            return;
        }
        Map<String, BaseInfo> modelMap = models.stream().collect(Collectors.toMap(ObjectInfo::getTitle, info -> info));
        for (ObjectInfo objectInfo : models) {
            objectInfo.fillRef(modelMap);
        }
    }

    private static void validate(Map<String, Object> map) {
        for (String checkItem : CHECK_ITEMS) {
            if (map.get(checkItem) == null) {
                throw new CustomException(Messages.INVALID_SWAGGER_JSON);
            }
        }
    }
}
