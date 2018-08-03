package com.xxm.review.utils.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 短信的工具类
 */
public class SmsUtils {

    /**
     * 备份短信的回调接口
     */
    public interface BackUpCallBack {

        /**
         * 进度条的最大刻度
         *
         * @param max 总进度
         */
        public void beforeBackUp(int max);

        /**
         * 备份过程中，增加的进度
         *
         * @param process 当前进度
         */
        public void onSmsBackUp(int process);
    }

    /**
     * 备份短信
     *
     * @param context  上下文
     * @param callBack 备份短信的回调接口
     * @throws Exception
     */
    public static void backupSms(Context context, BackUpCallBack callBack) throws Exception {
        ContentResolver resolver = context.getContentResolver();
        File file = new File(Environment.getExternalStorageDirectory(), "backup.xml");
        FileOutputStream fos = new FileOutputStream(file);
        // 把短信一条一条读取出来，按照一定的格式写到文件里
        XmlSerializer xmlSerializer = Xml.newSerializer();// 获取xml的生成器（序列化器）
        // 初始化生成器
        xmlSerializer.setOutput(fos, "utf-8");
        xmlSerializer.startDocument("utf-8", true);
        xmlSerializer.startTag(null, "smss");

        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = resolver.query(uri, new String[]{"body", "address", "type", "date"}, null, null, null);
        // 进度条的最大刻度
        int max = cursor.getCount();
//		pd.setMax(max);
        callBack.beforeBackUp(max);
        xmlSerializer.attribute(null, "max", max + "");
        int process = 0;
        while (cursor.moveToNext()) {
            Thread.sleep(500);
            String body = cursor.getString(0);
            String address = cursor.getString(1);
            String type = cursor.getString(2);
            String date = cursor.getString(3);
            xmlSerializer.startTag(null, "sms");

            xmlSerializer.startTag(null, "body");
            xmlSerializer.text(body);
            xmlSerializer.endTag(null, "body");

            xmlSerializer.startTag(null, "address");
            xmlSerializer.text(address);
            xmlSerializer.endTag(null, "address");

            xmlSerializer.startTag(null, "type");
            xmlSerializer.text(type);
            xmlSerializer.endTag(null, "type");

            xmlSerializer.startTag(null, "date");
            xmlSerializer.text(date);
            xmlSerializer.endTag(null, "date");

            xmlSerializer.endTag(null, "sms");
            // 备份过程中，增加进度
            process++;
//			pd.setProgress(process);
            callBack.onSmsBackUp(process);
        }
        cursor.close();
        xmlSerializer.endTag(null, "smss");
        xmlSerializer.endDocument();
        fos.close();
    }


    public static void restoerSms(Context context, boolean flag) {
        Uri uri = Uri.parse("content://sms/");
        if (flag) {
            context.getContentResolver().delete(uri, null, null);
        }
        //1、读取sd卡上的xml文件
        //Xml.newPullParser();

        //2、读取max

        //3、读取每一条信息，"body", "address", "type", "date"

        //4、把信息插入到系统图短息应用

        //例子
        ContentValues values = new ContentValues();
        values.put("body", "content");
        values.put("address", "address");
        values.put("type", "1");
        values.put("date", "date");
        context.getContentResolver().insert(uri, values);

    }


}
