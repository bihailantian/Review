package com.xxm.review.activity;

/**
 * Created by xubin on 17-2-6.
 * <p>
 * Function:
 */
public class ChatMessageCategory {

    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int AUDIO_FILE = 3;
    public static final int VIDEO_FILE = 4;

    public static final int ALERT = 5;// 警报
    public static final int LOCATION = 6; //位置
    public static final int LOCATION_SHARE = 7;// 位置实时分享

    public static final int SHARE = 8;    // 第三方内容分享

    public static final int CONTROL = 9; // 控制命令，消息体中再封装了ControlCommand.ControlFieldBean，用来打包不同类型的控制

    public static final int REPORT_DISPATCH = 10;//拍传分发，走的NgnContentType.TEXT_REPORT_DISPATCH

    public static final int VIDEO_LIVE = 11;//直播消息类型

    public static final int NOTIFICATION_GETUP = 15; // 叫哨通知，收到后持续震动，响铃
    public static final int NOTIFICATION_TASK = 16;  // 任务通知
    public static final int NOTIFICATION_REPORT = 17;    // 上报通知
    public static final int NOTIFICATION_CLOCK = 18; // 考勤通知

    public static final int IMAGE_DISTINGUISH = 19;//图片识别


    public static final int ALERT_REPORT = 20;// 其他报警上报(断电报警等)

    public static final int OTHER_FILE = 21;//其他文件消息（压缩包、Word、PDF、TXT、XLS、XLSX）

    //=========== 非TEXT PLAIN类型，指定各种通话类型 ====================
    public static final int AUDIO = 100;
    public static final int VIDEO = 101;
    public static final int AUDIO_PTT = 102;
    public static final int VIDEO_PTT = 103;
    public static final int AUDIO_MEETING = 104;
    public static final int VIDEO_MEETING = 105;
    public static final int AUDIO_BROADCAST = 106;
    public static final int VIDEO_BROADCAST = 107;
    public static final int PTT_AUDIO_FILE = 108;//对讲录音
    //================================================
}
