package com.thoughtworks.bridge2delivery.swagger.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
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

    @Override
    public String getJsonExample() {
        return refInfo.getJsonExample();
    }

    @Override
    public String getRefName() {
        return this.refInfo.getRefName();
    }

    @Override
    public String getFullType() {
        return this.refInfo.getFullType();
    }
}
