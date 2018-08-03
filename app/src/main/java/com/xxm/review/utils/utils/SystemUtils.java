package com.xxm.review.utils.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 系统信息工具类
 *
 * @author Administrator
 */
public class SystemUtils {

    private static BufferedReader br;

    /**
     * 获取设备上正在运行的进程总数
     *
     * @param context 上下文
     */
    public static int getRunningProcresscount(Context context) {
        // ActivityManager 进程管理器，管理手机的活动信息，动态的内容
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        return infos.size();
    }

    /**
     * 获取手机可用的剩余内存
     *
     * @return
     */
    public static long getAvailMen(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo outInfo = new MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.availMem;

    }

    /**
     * 获取手机可用的总内存
     *
     * @return
     */
    public static long getTotalMen(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo outInfo = new MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.totalMem;

    }

    /*
     *
     * /** 获取可用内存
     *
     * @return 单位是字节
     */
    public static long getAvailRam(Context context) {
        /*
         * //下面的api totalmem只能在16以上版本下使用。 ActivityManager am = (ActivityManager)
		 * context.getSystemService(Context.ACTIVITY_SERVICE); MemoryInfo
		 * outInfo = new ActivityManager.MemoryInfo();
		 * am.getMemoryInfo(outInfo); return outInfo.availMem;
		 */

        try {
            File file = new File("/proc/meminfo");
            FileInputStream fis = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(fis));
            String line = br.readLine();
            StringBuffer sb = new StringBuffer();
            for (char c : line.toCharArray()) {
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            return Integer.parseInt(sb.toString()) * 1024l;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

}
