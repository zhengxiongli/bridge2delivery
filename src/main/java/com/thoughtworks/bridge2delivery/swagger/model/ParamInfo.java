package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.utils.JSONUtils;
import com.thoughtworks.bridge2delivery.template.Template;
import lombok.Data;

import java.util.Map;

@Data
public class ParamInfo implements TypeInterface {
    @Template(description = "参数名")
    private String name;
    @Template(description = "传递类型")
    private String paramType;
    @Template(description = "描述")
    private String description;
    private DataType dataType;
    @Template(description = "默认值")
    private String defaultVal;
    @Template(description = "是否允许空值")
    private boolean allowEmptyValue;
    @Template(description = "是否必需")
    private boolean required;
    private String format;
    private BaseInfo schema;
    @Template(description = "数据类型")
    private String fullType;

    public ParamInfo build(Map<String, Map> paramInfo, Map<String, BaseInfo> models) {
        this.setName(JSONUtils.getMapValueAndToString(paramInfo, "name"));
        this.setParamType(JSONUtils.getMapValueAndToString(paramInfo, "in"));
        this.setDescription(JSONUtils.getMapValueAndToString(paramInfo, "description"));
        this.setDefaultVal(JSONUtils.getMapValueAndToString(paramInfo, "default"));
        this.setAllowEmptyValue(Boolean.parseBoolean(JSONUtils.getMapValueAndToString(paramInfo,
                "allowEmptyValue")));
        this.setRequired(Boolean.parseBoolean(JSONUtils.getMapValueAndToString(paramInfo,
                "required")));
        this.setFormat(JSONUtils.getMapValueAndToString(paramInfo, "format"));
        this.setDataType(DataType.fromValue(JSONUtils.getMapValueAndToString(paramInfo, "type")));
        fillSchema(paramInfo, models);
        return this;
    }

    private void fillSchema(Map<String, Map> paramInfo, Map<String, BaseInfo> models) {
        Map schema = paramInfo.get("schema");
        boolean isArray = this.getDataType() != null && this.getDataType() == DataType.ARRAY;
        if (!isArray && schema == null) {
            return;
        }
        if (schema == null) {
            BaseInfo baseInfo = BaseInfo.newInstance(paramInfo);
            this.setSchema(baseInfo.build(paramInfo));
            this.setDataType(baseInfo.getType());
            this.getSchema().fillRef(models);
        } else {
            String ref = JSONUtils.getRef(schema);
            if (ref != null) {
                this.schema = models.get(ref);
                this.setDataType(DataType.OBJECT);
            } else {
                BaseInfo baseInfo = BaseInfo.newInstance(schema);
                this.setSchema(baseInfo.build(schema));
                this.setDataType(baseInfo.getType());
                this.getSchema().fillRef(models);
            }
        }
    }

    @Override
    public String getFullType() {
        if (schema == null && dataType == null) {
            return "";
        }
        return this.schema == null ? (dataType.getValue() + (format != null ? "(" + format + ")" : "")) :
                this.schema.getFullType();
    }
}
