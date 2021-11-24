package com.xxm.review.utils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class JsonParseUtil<T> {


    public T parseJson(String json)  {
        String result = json;
        if (result == null) {
            return null;
        }
        if (getClass().getGenericSuperclass() == JsonParseUtil.class) {
            return (T) result;// 默认返回String
        }
        if (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0] == String.class) {
            return (T) result;// String类型直接返回
        }
        Type type = $Gson$Types.canonicalize(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        return new Gson().fromJson(result, type);
    }

}
