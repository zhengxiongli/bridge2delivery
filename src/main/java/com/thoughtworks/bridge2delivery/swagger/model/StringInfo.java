package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class StringInfo extends BaseInfo {
    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        builder.append(JSONUtils.JSON_QUOTE);
        builder.append("string");
        builder.append(JSONUtils.JSON_QUOTE);
        return builder.toString();
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.STRING);
        return this;
    }
}
