package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import com.thoughtworks.bridge2delivery.template.Template;
import lombok.Data;

import java.util.Map;

@Data
public class Response implements TypeInterface {
    @Template(description = "状态码")
    private String status;
    @Template(description = "描述")
    private String description;
    private DataType dataType;
    private BaseInfo schema;
    @Template(description = "数据类型")
    private String fullType;

    public void build(Map<String, Map> map, Map<String, BaseInfo> models) {
        this.setDescription(JSONUtils.getMapValueAndToString(map, "description"));
        this.fillSchema(map, models);
    }

    public String getRefName() {
        if (schema == null || (schema.getType() != DataType.ARRAY && schema.getType() != DataType.OBJECT)) {
            return "";
        }
        return schema.getRefName();
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

    @Override
    public String getFullType() {
        if (schema == null && dataType == null) {
            return "";
        }
        return this.getSchema() == null ? dataType.getValue() : this.getSchema().getFullType();
    }
}
