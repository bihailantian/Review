package com.xxm.review.utils;

import android.content.Context;
import android.media.MediaDrm;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.UUID;

public class ImeiUtil {

    private static final String TAG = ImeiUtil.class.getSimpleName();

    public static String getMachineImei(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class clazz = manager.getClass();
        String imei = "";
        try {
            Method getImei = clazz.getDeclaredMethod("getImei", int.class);//(int slotId)
            getImei.setAccessible(true);
            imei = (String) getImei.invoke(manager);
        } catch (Exception e) {
            Log.e(TAG, "getMachineImei: " + e.toString());
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
            Log.e(TAG, "getPhoneInfo: " + e.toString());
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
            Log.e(TAG, "getMethodParamTypes: " + e.toString());
        }
        return params;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getWidevineId() {
        String sRet = "";

        UUID WIDEVINE_UUID = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        MediaDrm mediaDrm = null;

        try {
            mediaDrm = new MediaDrm(WIDEVINE_UUID);
            byte[] widevineId = mediaDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(widevineId);

            sRet = bytesToHex(md.digest()); //we convert byte[] to hex for our purposes
        } catch (Exception e) {
            //WIDEVINE is not available
            Log.e(TAG, "getWidevineSN.WIDEVINE is not available \n" + Log.getStackTraceString(e));
        } finally {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (null != mediaDrm) {
                    mediaDrm.close();
                }
            } else {
                if (null != mediaDrm) {
                    mediaDrm.release();
                }
            }
        }

        return sRet;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
