package com.thoughtworks.bridge2delivery.swagger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
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
