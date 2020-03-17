package com.thoughtworks.bridge2delivery.swagger.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class IntegerInfo extends BaseInfo {
    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        builder.append(0);
        return builder.toString();
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.INTEGER);
        return this;
    }

    @Override
    public String getFullType() {
        return this.getFormat() == null ? super.getFullType() : this.getFormat();
    }
}
