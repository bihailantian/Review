package com.xxm.review.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

public class ImeiUtil {

    private static final String TAG = ImeiUtil.class.getSimpleName();

    public static String getMachineImei(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class clazz = manager.getClass();
        String imei = "";
        try {
            Method getImei=clazz.getDeclaredMethod("getImei",int.class);//(int slotId)
            getImei.setAccessible(true);
            imei = (String) getImei.invoke(manager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 获取IMEI
     *
     * @param context 上下文
     * @param soltId  为卡槽Id，它的值为 0、1
     * @return imei
     */
    public static String getDeviced(Context context, int soltId) {
        return (String) getPhoneInfo(soltId, "getDeviceId", context);
    }


    public static Object getPhoneInfo(int subId, String methodName, Context context) {
        Object value = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= 21) {
                Method method = tm.getClass().getMethod(methodName, getMethodParamTypes(methodName));
                if (subId >= 0) {
                    value = method.invoke(tm, subId);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return value;
    }


    public static Class[] getMethodParamTypes(String methodName) {
        Class[] params = null;
        try {
            Method[] methods = TelephonyManager.class.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodName.equals(methods[i].getName())) {
                    params = methods[i].getParameterTypes();
                    if (params.length >= 1) {
                        Log.d(TAG, "length:" + params.length);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return params;
    }
}
