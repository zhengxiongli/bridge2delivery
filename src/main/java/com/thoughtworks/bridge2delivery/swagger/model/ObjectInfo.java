package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import com.thoughtworks.bridge2delivery.template.Template;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class ObjectInfo extends BaseInfo {
    @Template(description = "标题")
    private String title;
    private String className;
    @Template(description = "属性列表")
    private List<BaseInfo> properties;

    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        if (CollectionUtils.isEmpty(properties)) {
            builder.append("{}");
            return builder.toString();
        }
        builder.append(JSONUtils.JSON_OBJ_START);
        String propertiesStr = properties.stream().map(p -> p.getJsonExample()).collect(Collectors.joining(","));
        builder.append(propertiesStr);
        builder.append(JSONUtils.JSON_OBJ_END);
        return builder.toString();
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setTitle(getMapValueAndToString(map, "title"));
        this.setClassName(getClassNames(this.getTitle(), 0));
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

    private void buildProperties(Map<String, Map> propertyMap) {
        if (CollectionUtils.isEmpty(propertyMap)) {
            return;
        }
        this.properties = new ArrayList<>(propertyMap.size());
        Iterator<Map.Entry<String, Map>> iterator = propertyMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map> entry = iterator.next();
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
