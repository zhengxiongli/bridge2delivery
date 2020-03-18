package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.template.Template;
import com.thoughtworks.bridge2delivery.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
public class ObjectInfo extends BaseInfo {
    @Template(description = "标题", order = 0)
    private String title;
    private String className;
    @Template(description = "属性列表", order = 2)
    private List<BaseInfo> properties;
    @Template(description = "描述", order = 1)
    private String description;

    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        if (CollectionUtils.isEmpty(properties)) {
            builder.append("{}");
            return builder.toString();
        }
        builder.append(JSONUtils.JSON_OBJ_START);
        String propertiesStr = properties.stream()
                .filter(it -> Objects.equals(it.getRefName(), title))
                .map(BaseInfo::getJsonExample).collect(Collectors.joining(", "));
        builder.append(propertiesStr);
        builder.append(JSONUtils.JSON_OBJ_END);
        return builder.toString();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setTitle(JSONUtils.getMapValueAndToString(map, "title"));
        this.setClassName(getClassNames(this.getTitle()));
        this.setType(DataType.OBJECT);
        buildProperties(map.get("properties"));
        return this;
    }

    @Override
    public void fillRef(Map<String, BaseInfo> refMap) {
        if (!CollectionUtils.isEmpty(this.getProperties())) {
            for (BaseInfo baseInfo : this.getProperties()) {
                baseInfo.fillRef(refMap);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void buildProperties(Map<String, Map> propertyMap) {
        if (CollectionUtils.isEmpty(propertyMap)) {
            return;
        }
        this.properties = new ArrayList<>(propertyMap.size());
        for (Map.Entry<String, Map> entry : propertyMap.entrySet()) {
            Map<String, Map> propertyInfo = entry.getValue();
            BaseInfo property = newInstance(propertyInfo);
            if (property == null) {
                continue;
            }
            property.setName(entry.getKey());
            property.build(propertyInfo);
            this.properties.add(property);
        }
    }

    @Override
    public String getRefName() {
        return title;
    }

    @Override
    public String getFullType() {
        return this.getRefName();
    }
}
