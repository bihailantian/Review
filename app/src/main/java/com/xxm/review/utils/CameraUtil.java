package com.xxm.review.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.MediaStore;

import java.util.List;

public class CameraUtil {


    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

}
