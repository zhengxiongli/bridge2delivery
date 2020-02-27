package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class BaseInfo {
    private String name;
    private DataType type;
    private String example;
    private String description;
    private String format;
    private String refName;

    public void setName(String name) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        this.name = name;
    }

    public void setExample(String example) {
        if (StringUtils.isEmpty(example)) {
            return;
        }
        this.example = example;
    }

    public void setDescription(String description) {
        if (StringUtils.isEmpty(description)) {
            return;
        }
        this.description = description;
    }

    public String getRefName() {
        return this.type.getValue();
    }

    public void setFormat(String format) {
        if (StringUtils.isEmpty(format)) {
            return;
        }
        this.format = format;
    }

    public String getValues() {
        return "";
    }

    public String getJsonExample() {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(JSONUtils.JSON_QUOTE);
        builder.append(name);
        builder.append(JSONUtils.JSON_QUOTE);
        builder.append(JSONUtils.JSON_COLON);
        return builder.toString();
    }

    public BaseInfo build(Map<String, Map> map) {
        this.setName(getMapValueAndToString(map, "name"));
        this.setDescription(getMapValueAndToString(map, "description"));
        this.setFormat(getMapValueAndToString(map, "format"));
        this.setExample(getMapValueAndToString(map, "example"));
        return this;
    }

    public void fillRef(Map<String, BaseInfo> refMap) {

    }

    protected String getClassNames(String title, int index) {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        String[] classNames = title.replaceAll("»", "").split("«");
        if (index < classNames.length) {
            return classNames[index];
        }
        return null;
    }

    protected  <T> String getMapValueAndToString(Map<T, Map> map, T key) {
        return JSONUtils.getMapValueAndToString(map, key);
    }

    protected String getRef(Map<String, Map> map) {
        if (map.get("$ref") == null) {
            return null;
        }
        Object ref = map.get("$ref");
        return ref.toString().replaceFirst("#/definitions/", "");
    }

    public static BaseInfo newInstance(Map<String, Map> map) {
        String typeStr = JSONUtils.getMapValueAndToString(map, "type");
        DataType type = typeStr == null ? DataType.REF : DataType.fromValue(typeStr);
        if (map.get("enum") != null) {
            type = DataType.ENUM;
        }
        BaseInfo instance;
        try {
            instance = type.getClazz().newInstance();
        } catch (Exception e) {
            log.error("new instance error with type:" + typeStr, e);
            return null;
        }
        return instance;
    }
}
