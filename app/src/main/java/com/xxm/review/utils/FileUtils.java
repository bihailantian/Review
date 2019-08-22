package com.xxm.review.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * 文件工具类
 */
public class FileUtils {

    /**
     * 删除指定目录下的文件
     *
     * @param filePath 文件路径
     * @return true:删除成功，false:删除失败
     */
    public static boolean deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() && file.delete();
    }


    /**
     * 读取Assets目录下的文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        StringBuilder result = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;

            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return result.toString();
        }

    }


    /**
     * 从文件读取json
     *
     * @param dirFile 文件所在目录
     * @return json字符串
     */
    public static String readJsonFile(String dirFile) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            fileReader = new FileReader(dirFile);
            bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println(line);
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        line = stringBuilder.toString();
        // System.out.println(line);
        return line;
    }


    /**
     * 根据文件路径截取文件名
     *
     * @param filePath 文件路径
     * @return fileName
     */
    public static String getFileName(String filePath) {
        String fileName = null;
        if (filePath != null && !filePath.isEmpty()) {
            fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length());
        }
        return fileName;
    }


}
