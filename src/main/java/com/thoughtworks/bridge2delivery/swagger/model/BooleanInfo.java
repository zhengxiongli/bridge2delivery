package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BooleanInfo extends BaseInfo {
    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        builder.append(JSONUtils.JSON_QUOTE);
        builder.append("true");
        builder.append(JSONUtils.JSON_QUOTE);
        return builder.toString();
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.BOOLEAN);
        return this;
    }
}
