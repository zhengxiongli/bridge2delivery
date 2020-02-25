package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private int status;
    private String description;
    private DataType dataType;
    private BaseInfo schema;

    public void build(Map<String, Map> map, Map<String, BaseInfo> models) {
        this.setDescription(JSONUtils.getMapValueAndToString(map, "description"));
        this.fillSchema(map, models);
    }

    private void fillSchema(Map<String, Map> map, Map<String, BaseInfo> models) {
        Map schema = map.get("schema");
        if (schema == null) {
            return;
        }
        String ref = JSONUtils.getRef(schema);
        if (ref != null) {
            this.schema = models.get(ref);
            this.setDataType(DataType.OBJECT);
        } else {
            BaseInfo baseInfo = BaseInfo.newInstance(schema);
            this.setSchema(baseInfo.build(schema));
            this.getSchema().fillRef(models);
            this.setDataType(baseInfo.getType());
        }
    }
}
