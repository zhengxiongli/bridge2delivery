package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class ArrayInfo extends BaseInfo {
    private BaseInfo items;

    @Override
    public String getJsonExample() {
        StringBuilder builder = new StringBuilder(super.getJsonExample());
        if (items == null) {
            builder.append("[]");
            return builder.toString();
        }

        builder.append(JSONUtils.JSON_LIST_START);
        builder.append(items.getJsonExample());
        builder.append(JSONUtils.JSON_LIST_END);
        return builder.toString();
    }

    @Override
    public BaseInfo build(Map<String, Map> map) {
        super.build(map);
        this.setType(DataType.ARRAY);
        this.items = newInstance(map.get("items")).build(map.get("items"));
        return this;
    }

    @Override
    public void fillRef(Map<String, BaseInfo> refMap) {
        if (items != null) {
            items.fillRef(refMap);
        }
    }

    @Override
    public String getRefName() {
        return "List«" + items.getRefName() + "»";
    }

    @Override
    public String getFullType() {
        return this.getRefName();
    }
}
