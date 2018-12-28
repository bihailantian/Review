package com.xxm.review.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtils {


    private static final String TAG = ImageUtils.class.getSimpleName();


    private ImageUtils() {
         /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path 图片的路径
     * @return Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    /**
     * 将Base64编码转换为图片，并保存
     *
     * @param base64Str Base64编码字符串
     * @param savePath  保存路径，需要带上图片的后缀名
     * @return true
     */
    public static boolean base64ToFile(String base64Str, String savePath) {
        byte[] data = Base64.decode(base64Str, Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(savePath);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 图片的base64码字符串转Bitmap对象
     *
     * @param base64Str 图片的base64码字符串
     * @return Bitmap对象
     */
    public static Bitmap stringToBitmap(String base64Str) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(base64Str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


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
