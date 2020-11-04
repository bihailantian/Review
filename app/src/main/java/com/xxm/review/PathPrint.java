package com.xxm.review;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.xxm.review.utils.ImeiUtil;

import java.io.File;

public class PathPrint {


    private static final String TAG = "PathPrint";

    public static void printPath(Activity activity){
        Log.d(TAG, "getPath()= " +  Environment.getExternalStorageDirectory().getPath() ); //<p>  /storage/emulated/0
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
            Log.d(TAG,"Environment.DIRECTORY_DOWNLOADS："+activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));  //<p> /storage/emulated/0/Android/data/com.xxm.review/files/Download
            Log.d(TAG,"getDataDirectory()："+Environment.getDataDirectory());  //<p> /data
        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.d(TAG,"############ IMEI ###############");
        try {
            Log.d(TAG, ImeiUtil.getMachineImei(activity));
            //Log.d(TAG, ImeiUtil.getDeviced(activity,1));
        } catch (Exception e) {
           Log.e(TAG,Log.getStackTraceString(e));
        }
        Log.d(TAG,"############ IMEI ###############");

    }
}
