package com.xxm.review.utils;

import com.google.gson.GsonBuilder;

public class FromJsonUtils {

    public static ResultData fromJson(String json, Class clazz) {
        return new GsonBuilder()
                .registerTypeAdapter(ResultData.class, new JsonFormatParser(clazz))
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create()
                .fromJson(json, ResultData.class);
    }
}