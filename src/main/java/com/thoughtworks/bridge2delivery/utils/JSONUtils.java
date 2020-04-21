package com.thoughtworks.bridge2delivery.utils;

import java.util.Map;

public final class JSONUtils {
    public static final String JSON_OBJ_START = "{";
    public static final String JSON_OBJ_END = "}";
    public static final String JSON_LIST_START = "[";
    public static final String JSON_LIST_END = "]";
    public static final String JSON_QUOTE = "\"";
    public static final String JSON_COLON = ":";
    public static final String WHITE_SPACE = " ";

    private JSONUtils() {

    }

    @SuppressWarnings("rawtypes")
    public static String getMapValueAndToString(Map<String, Map> map, String key) {
        if (map == null) {
            return null;
        }
        Object value = map.get(key);
        return value == null ? null : value.toString();
    }

    public static String getRef(Map<String, Object> map) {
        if (map.get("$ref") == null) {
            return null;
        }
        Object ref = map.get("$ref");
        return ref.toString().replaceFirst("#/definitions/", "");
    }
}
