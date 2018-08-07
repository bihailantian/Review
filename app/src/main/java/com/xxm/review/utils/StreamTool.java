package com.xxm.review.utils;

import java.io.ByteArrayOutputStream;
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

}