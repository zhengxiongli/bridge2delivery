package com.thoughtworks.bridge2delivery.swagger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class RefInfo extends BaseInfo {
    private String ref;
    private BaseInfo refInfo;

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.REF);
        this.setRef(getRef(map));
        return this;
    }

    @Override
    public void fillRef(Map<String, BaseInfo> refMap) {
        this.refInfo = refMap.get(ref);
    }
}
