package com.thoughtworks.bridge2delivery.swagger.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FileInfo extends BaseInfo {
    @Override
    public String getJsonExample() {
        return super.getJsonExample() + "\"\"";
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.FILE);
        return this;
    }
}
