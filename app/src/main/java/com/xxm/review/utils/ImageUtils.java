package com.xxm.review.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class ImageUtils {


    private static final String TAG = ImageUtils.class.getSimpleName();

    /**
     * 保存 bitmap，调用函数时记得把文件的扩展名带上
     *
     * @param bitmap  Bitmap
     * @param bitName 文件名
     * @param path    保存路径
     */
    public static void saveBitmap(Context context, Bitmap bitmap, String bitName, String path) {
        //"/sdcard/DCIM/Camera/"

        File fileDir = new File(path);
        Log.d(TAG, path + " 是否已存在 " + fileDir.exists());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(path, bitName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
