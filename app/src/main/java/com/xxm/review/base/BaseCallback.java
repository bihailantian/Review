package com.xxm.review.base;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 解析json字符串基类
 *
 * @param <T> json字符串对应的实体类泛型
 */
public class BaseCallback<T> {

    /**
     * 通过反射获取泛型 T 的Type
     *
     * @return Type的对象typeOfT
     */
    private Type getType() {
        return ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    /**
     * 解析json
     *
     * @param strJson json字符串
     * @return T 对象
     */
    public T parseJson(String strJson) {
        Gson gson = new Gson();
        return gson.fromJson(strJson, getType());
    }


}
