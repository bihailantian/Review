package com.xxm.review.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;

public class ServiceUtils {


    /**
     * 判断Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service 类全名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        // 校验服务是否还活着
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<RunningServiceInfo> serviceInfoList = am.getRunningServices(100);
            for (RunningServiceInfo info : serviceInfoList) {
                String name = info.service.getClassName();
                if (name.equals(serviceName)) {
                    return true;
                }
            }
        }
        return false;

    }
}
