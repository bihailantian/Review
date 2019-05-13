package com.xxm.review.utils;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 数据流工具类
 */
public class StreamTool {

    /**
     * 读取流中的数据
     *
     * @param inStream 输入流
     */
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    public static void getString(String str) {

        String filePath = null;

        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if (hasSDCard) {

            filePath =Environment.getExternalStorageDirectory().toString() + File.separator +"hello.txt";

        } else {

            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + "hello.txt";
        }


        try {

            File file = new File(filePath);

            if (!file.exists()) {

                File dir = new File(file.getParent());

                dir.mkdirs();

                file.createNewFile();

            }

            FileOutputStream outStream = new FileOutputStream(file);

            outStream.write(str.getBytes());

            outStream.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}