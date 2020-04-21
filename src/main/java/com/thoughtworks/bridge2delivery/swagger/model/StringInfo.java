package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StringInfo extends BaseInfo {
    @Override
    public String getJsonExample() {
        return super.getJsonExample() + JSONUtils.JSON_QUOTE +
                "string" +
                JSONUtils.JSON_QUOTE;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.STRING);
        return this;
    }
}
