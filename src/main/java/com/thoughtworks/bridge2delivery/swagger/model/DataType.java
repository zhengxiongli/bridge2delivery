package com.thoughtworks.bridge2delivery.swagger.model;

import com.thoughtworks.bridge2delivery.swagger.exception.TypeNotFoundException;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public enum  DataType {
    STRING("string", StringInfo.class), INTEGER("integer", IntegerInfo.class),
    ARRAY("array", ArrayInfo.class), FILE("file", FileInfo.class),
    OBJECT("object", ObjectInfo.class), ENUM("enum", EnumInfo.class),
    REF("ref", RefInfo.class), BOOLEAN("boolean", BooleanInfo.class);

    private String value;
    private Class<? extends BaseInfo> clazz;

    DataType(String value, Class<? extends BaseInfo> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }

    public Class<? extends BaseInfo> getClazz() {
        return this.clazz;
    }

    public static DataType fromValue(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return Arrays.stream(DataType.values()).filter(t -> t.getValue().equalsIgnoreCase(value))
                .findFirst().orElseThrow(TypeNotFoundException::new);
    }
}
