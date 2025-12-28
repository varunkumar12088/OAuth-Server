package com.learning.utils;

import tools.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static String toJsonStr(Object object) {
        try {
            return OBJECT_MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        }  catch (Exception e) {
            return String.valueOf(object);
        }
    }
}
