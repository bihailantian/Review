package com.xxm.review.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

/**
 * 系统信息工具类
 *
 * @author Administrator
 */
public class SystemUtils {
    private static final String TAG = "SystemUtils";

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

    /***
     * 获取CPU的id
     * @return
     */
    public static String getCpuInfo() {
        String cpuInfo = "";
        try {
            File file = new File("/proc/cpuinfo");
            FileReader reader = new FileReader(file);
            char[] bb = new char[1024];
            int n;
            while ((n = reader.read(bb)) != -1) {
                cpuInfo += new String(bb, 0, n);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "cpuInfo=" + cpuInfo);
        if (!TextUtils.isEmpty(cpuInfo) || cpuInfo.length() > 2) {
            cpuInfo = cpuInfo.substring(cpuInfo.lastIndexOf(":") + 1, cpuInfo.length()).trim();
        }
        return cpuInfo;
    }

    /***
     * 获取设备以太网的Mac地址
     * @return
     */
    public static String getEthMAC() {
        String macSerial = null;
        String str = "";
        try {
            Process ex = Runtime.getRuntime().exec("cat /sys/class/net/eth0/address");
            InputStreamReader ir = new InputStreamReader(ex.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            while (null != str) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }
        return macSerial != null && !macSerial.equals("") && macSerial.length() == 17 ? macSerial.toUpperCase() : "";
    }

}
