package com.xxm.review;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.xxm.review.utils.DeviceIdUtil;
import com.xxm.review.utils.ImeiUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathPrint {


    private static final String TAG = "PathPrint";

    public static void replaceHost() {
        String url = "http://20.20.1.216:80/upload/msg/video/8143/20231025/16982162804140.mp4";
        Uri uri = Uri.parse(url);
        String ip = "20.20.1.218";
        String port = "8090";
        String host = "http://" + ip + ":" + port;
        Log.d(TAG, "host=" + uri.getHost());
        Log.d(TAG, "path=" + uri.getPath());
        Log.d(TAG, "query=" + uri.getQuery());
    }

    public static void printPath(Activity activity) {
        long uptimeInMillis = SystemClock.elapsedRealtime();
        long uptimeInSeconds = uptimeInMillis / 1000;
        boolean ret = uptimeInSeconds < 60 * 20;
        Log.d(TAG, "开机时间小于20分钟：" + ret + ",uptimeInSeconds=" + uptimeInSeconds);

        replaceHost();
        try {
            Log.d(TAG, "==============host====================");
            String url = "http://null/api/client/getgis.php?number=-1&acc=0";
            Uri uri = Uri.parse(url);
            String host = uri.getHost();
            Log.d(TAG, "host=" + host);
            Log.d(TAG, "==============host====================");
        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.d(TAG, "getPath()= " + Environment.getExternalStorageDirectory().getPath()); //<p>  /storage/emulated/0
        Log.d(TAG, "getPackageName()= " + activity.getPackageName());
        Log.d(TAG, "getApplicationInfo().packageName= " + activity.getApplicationInfo().packageName);
        Log.d(TAG, "getApplicationInfo().processName= " + activity.getApplicationInfo().processName);
        Log.d(TAG, "getApplication().getPackageName()= " + activity.getApplication().getPackageName());
        Log.d(TAG, "目录= " + Environment.getExternalStorageDirectory().getAbsolutePath()); //<p>  /storage/emulated/0
        Log.d(TAG, "sd卡: " + Environment.getExternalStorageState());
        Log.d(TAG, "sd卡: " + Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)); //判断sd卡是否可读
        Log.d(TAG, "文件路径: " + Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Review"); //<p>   /storage/emulated/0/Review
        Log.d(TAG, "文件路径: " + Environment.getExternalStorageDirectory()); //<p>  /storage/emulated/0
        Log.d(TAG, "Build.VERSION.SDK_INT= " + Build.VERSION.SDK_INT);
        try {
            Log.d(TAG, "getApplicationInfo().packageName= " + activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "Environment.DIRECTORY_DOWNLOADS：" + activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));  //<p> /storage/emulated/0/Android/data/com.xxm.review/files/Download
            //Log.d(TAG, "getExternalCacheDirs 1：" + activity.getExternalCacheDirs()[1].getAbsolutePath());  //<p> /storage/emulated/0/Android/data/com.xxm.review/files/Download
            Log.d(TAG, "cache：" + activity.getExternalFilesDir("cache").getPath()); //  /storage/emulated/0/Android/data/com.xxm.review/files/cache
            Log.d(TAG, "getExternalFilesDir：" + activity.getExternalFilesDir(null).getPath()); //  /storage/emulated/0/Android/data/com.xxm.review/files
            Log.d(TAG, "getDataDirectory()：" + Environment.getDataDirectory());  //<p> /data
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "cacheDir：" + activity.getCacheDir().getPath());  ///  data/user/0/com.xxm.review/cache

        Log.d(TAG, "############ IMEI ###############");
        try {
            String deviceId = DeviceIdUtil.getDeviceId(activity);
            Log.d(TAG, "deviceId=" + deviceId + ", length=" + deviceId.length());
            Log.d(TAG, "machineImei=" + ImeiUtil.getMachineImei(activity));
            Log.d(TAG, "Imei 0 =" + ImeiUtil.getDeviced(activity, 0));
            Log.d(TAG, "Imei 1 =" + ImeiUtil.getDeviced(activity, 1));
            Log.d(TAG, "WidevineId=" + ImeiUtil.getWidevineId());
            //Log.d(TAG, ImeiUtil.getDeviced(activity,1));
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        Log.d(TAG, "############ IMEI ###############");

        File file = new File("/data/user_de/");

        Log.d(TAG, "/data/user_de : exists=" + file.exists() + ",getAbsolutePath=" + file.getAbsolutePath() + ",canRead=" + file.canRead());

        long firstInstallTime = getFirstInstallTime(activity);
        Log.d(TAG, "firstInstallTime=" + firstInstallTime);
        SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstInstallTimeStr = spdate.format(new Date(firstInstallTime));
        Log.d(TAG, "firstInstallTimeStr=" + firstInstallTimeStr);


    }


    public static long getFirstInstallTime(Context context) {
        if (context == null) {
            return 0;
        }
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.firstInstallTime;
    }
}
