package com.xxm.review.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获取apk信息
 */
public class ApkInfoTool {
    private static final String TAG = "ApkInfoTool";
	public static final String KEY_APP_KEY = "JPUSH_APPKEY";
    /**
     * @param context Context上下文
     * @param apkPath apk在SD中的路径
     * @return
     */
    public static Drawable getApkIcon(Context context, String apkPath) {

        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            return appInfo.loadIcon(pm);
        }
        return null;
    }

    /**
     * 获取apk的VersionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取apk的VersionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 备份data/app目录下本程序的apk安装文件到SD卡根目录下
     *
     * @param packageName
     * @param mActivity
     * @throws IOException
     */
    public static void backupApp(String packageName, Activity mActivity)
            throws IOException {
        // 存放位置
        String newFile = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        String oldFile = null;
        try {
            // 原始位置
            oldFile = mActivity.getPackageManager().getApplicationInfo(
                    packageName, 0).sourceDir;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(newFile);
        System.out.println(oldFile);

        File in = new File(oldFile);
        File out = new File(newFile + packageName + ".apk");
        if (!out.exists()) {
            out.createNewFile();
		}
		Log.i(TAG, "文件备份成功！" + "存放于" + newFile + "目录下");

		FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        int count;
        // 文件太大的话，我觉得需要修改
        byte[] buffer = new byte[256 * 1024];
        while ((count = fis.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }

        fis.close();
        fos.flush();
        fos.close();
    }

	// 取得AppKey
	public static String getAppKey(Context context) {
		Bundle metaData = null;
		String appKey = null;
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (null != ai)
				metaData = ai.metaData;
			if (null != metaData) {
				appKey = metaData.getString(KEY_APP_KEY);
				if ((null == appKey) || appKey.length() != 24) {
					appKey = null;
				}
			}
		} catch (NameNotFoundException e) {

		}
		return appKey;
	}
}
