package com.xxm.review.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;

import java.util.Set;

/**
 * Sharedpreferences工具类
 */
public class SharePrefUtil {

    private static final String COOKINGDIY = "cookingdiy";  //sharedPreferences保存数据的文件名
    private static final String USERINFOS = "userinfos";  //保存记住多组用户账户和密码数据的文件名


    /**
     * @param context 上下文
     * @param key     The name of the preference to retrieve.
     * @param value   存储的数值
     */
    public static void putLong(Context context, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKINGDIY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }


    /**
     * 获取int数据
     *
     * @param context  上下文
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return 一个int型的数
     */
    public static long getLong(Context context, String key, int defValue) {
        SharedPreferences sharePre = context.getSharedPreferences(COOKINGDIY, Context.MODE_PRIVATE);
        return sharePre.getLong(key, defValue);
    }


    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     The name of the preference to retrieve.
     * @return 字符串
     */
    public static String getString(Context context, String key, @Nullable String defValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKINGDIY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }


    /**
     * 存储字符串
     *
     * @param context 上下文
     * @param key     The name of the preference to retrieve.
     * @param value   值
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKINGDIY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取布尔值
     *
     * @param context      上下文
     * @param key          key
     * @param defaultValue key不存在获取的值
     * @return 布尔值
     */
    public static Boolean getBoolean(Context context, String key, Boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKINGDIY, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 存储布尔值
     *
     * @param context 上下文
     * @param key     key
     * @param value   值
     */
    public static void setBoolean(Context context, String key, Boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKINGDIY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * 保存用户名和密码
     *
     * @param userName 用户名
     * @param password 密码
     */
    public static void saveUserInfos(Context context, String userName, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERINFOS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(userName, password);
        editor.commit();
    }


    /**
     * 删除保存用户名和密码
     *
     * @param userName 用户名
     */
    public static void removeUserInfos(Context context, String userName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERINFOS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(userName);
        editor.commit();
    }


    /**
     * 保存用户名和密码
     *
     * @param userName 用户名
     * @param set      密码
     */
    private void saveUserInfos(Context context, String userName, Set<String> set) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USERINFOS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(userName, set);
        editor.commit();
    }


}
