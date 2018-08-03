package com.xxm.review.utils.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;

public class ServerUtils {

    public static boolean isServiceRunning(Context context, String serviceName) {
        // 校验服务是否还活着
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = am.getRunningServices(100);
        for (RunningServiceInfo info : infos) {
            String name = info.service.getClassName();
            if (name.equals(serviceName)) {
                return true;
            }
        }

        return false;

    }
}
