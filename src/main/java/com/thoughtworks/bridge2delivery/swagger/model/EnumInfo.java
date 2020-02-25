package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnumInfo extends BaseInfo {
    private List<String> values;

    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        builder.append(JSONUtils.JSON_QUOTE);
        builder.append(values.get(0));
        builder.append(JSONUtils.JSON_QUOTE);
        return builder.toString();
    }

    @Override
    public String getValues() {
        return values.toString();
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.ENUM);
        Object enums = map.get("enum");
        this.setValues((List) enums);
        return this;
    }
}
