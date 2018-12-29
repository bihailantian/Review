package com.xxm.review.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtils {

    private GsonUtils() {
         /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 转成list
     *
     * @param strJson json字符串
     * @param cls     Class<T>对象
     * @return 解析结果集合list<T>
     */
    public static <T> List<T> GsonToList(String strJson, Class<T> cls) {
        List<T> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());

        return list;
    }
}
