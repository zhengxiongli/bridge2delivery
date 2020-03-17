package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.template.Template;
import com.thoughtworks.bridge2delivery.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Slf4j
public class BaseInfo implements TypeInterface {
    @Template(description = "属性名")
    private String name;
    private DataType type;
    @Template(description = "示列", order = 3)
    private String example;
    @Template(description = "描述", order = 2)
    private String description;
    private String format;
    private String refName;
    @Template(description = "JSON示列", order = 4)
    private String jsonExample;
    @Template(description = "数据类型", order = 1)
    private String fullType;

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
        return JSONUtils.JSON_QUOTE +
                name +
                JSONUtils.JSON_QUOTE +
                JSONUtils.JSON_COLON +
                JSONUtils.WHITE_SPACE;
    }

    @SuppressWarnings({"rawtypes"})
    public BaseInfo build(Map<String, Map> map) {
        this.setName(JSONUtils.getMapValueAndToString(map, "name"));
        this.setDescription(JSONUtils.getMapValueAndToString(map, "description"));
        this.setFormat(JSONUtils.getMapValueAndToString(map, "format"));
        this.setExample(JSONUtils.getMapValueAndToString(map, "example"));
        return this;
    }

    public void fillRef(Map<String, BaseInfo> refMap) {

    }

    protected String getClassNames(String title) {
        if (StringUtils.isEmpty(title)) {
            return null;
        }
        String[] classNames = title.replaceAll("»", "").split("«");
        if (0 < classNames.length) {
            return classNames[0];
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    protected String getRef(Map<String, Map> map) {
        if (map.get("$ref") == null) {
            return null;
        }
        Object ref = map.get("$ref");
        return ref.toString().replaceFirst("#/definitions/", "");
    }

    @SuppressWarnings("rawtypes")
    public static BaseInfo newInstance(Map<String, Map> map) {
        String typeStr = JSONUtils.getMapValueAndToString(map, "type");
        DataType type = typeStr == null ? DataType.REF : DataType.fromValue(typeStr);
        if (map.get("enum") != null) {
            type = DataType.ENUM;
        }
        BaseInfo instance;
        try {
            instance = Objects.requireNonNull(type).getClazz().newInstance();
        } catch (Exception e) {
            log.error("new instance error with type:" + typeStr, e);
            return null;
        }
        return instance;
    }

    @Override
    public String getFullType() {
        return this.getType() == null ? "" : this.getType().getValue();
    }
}
