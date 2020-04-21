package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.template.Template;
import com.thoughtworks.bridge2delivery.utils.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Response implements TypeInterface {
    @Template(description = "状态码")
    private String status;
    @Template(description = "描述", order = 2)
    private String description;
    private DataType dataType;
    private BaseInfo schema;
    @Template(description = "数据类型", order = 1)
    private String fullType;

    @SuppressWarnings("rawtypes")
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

    @SuppressWarnings({"rawtypes", "unchecked"})
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
            this.setSchema(baseInfo != null ? baseInfo.build(schema) : null);
            this.getSchema().fillRef(models);
            this.setDataType(baseInfo != null ? baseInfo.getType() : null);
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
