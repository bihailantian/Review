package com.xxm.review.activity;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.xxm.review.R;

public class NotificationActivity extends AppCompatActivity {

    private static final int NO_3 = 1011;
    private static final int NO_4 = 1012;
    private static final String CHANNEL_ID_WARN = "nyd001";
    private static final String TAG = "NotificationActivity";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == NO_3) {

            }
        }
    };

    NotificationManager mNotifManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNotifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showProgressNotification(View view) {
        final Builder builder;
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Builder(this, getChannelId(manager, CHANNEL_ID_WARN, "预警"));
        } else {
            builder = new Builder(this);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher1);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        manager.notify(NO_3, builder.build());
        builder.setProgress(100, 0, false);


        //下载以及安装线程模拟
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                builder.setProgress(100, i, false);

                //下载进度提示
                String contentText = "下载" + i + "%";
                Log.d(TAG, "contentText=" + contentText);
                builder.setContentText(contentText).setWhen(System.currentTimeMillis());
                manager.notify(NO_3, builder.build()); //先更新内容，再notify,否则会出现进度更新不到100%
                try {
                    Thread.sleep(100);//演示休眠50毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //下载完成后更改标题以及提示信息
            builder.setContentTitle("开始安装");
            builder.setContentText("安装中...");
            //设置进度为不确定，用于模拟安装
            builder.setProgress(0, 0, true);
            manager.notify(NO_3, builder.build());
            //manager.cancel(NO_3);//设置关闭通知栏
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    manager.cancel(NO_3);//设置关闭通知栏
                }
            }, 3000);
        }).start();


    }

    public void showProgressNotification2(View view) {
        for (int i = 0; i <= 100; i++) {
            //下载进度提示
            String contentText = "下载" + i + "%";
            Log.d(TAG, "contentText=" + contentText);
            showProgressNotification("下载", contentText, 100, i);
            try {
                Thread.sleep(100);//演示休眠100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private void showProgressNotification(String contentTitle, String contentText, int max, int progress) {
        final Builder builder;
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Builder(this, getChannelId(manager, CHANNEL_ID_WARN, "通知测试"));
        } else {
            builder = new Builder(this);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(contentTitle);
        //builder.setContentText("正在下载");
        builder.setProgress(max, progress, false);

        builder.setContentText(contentText).setWhen(System.currentTimeMillis());
        manager.notify(NO_3, builder.build()); //先更新内容，再notify,否则会出现进度更新不到100%


        if (max == progress) {
            Log.d(TAG,"max == progress ="+ progress);
            //下载完成后更改标题以及提示信息
            builder.setContentTitle("开始安装");
            builder.setContentText("安装中...");
            //设置进度为不确定，用于模拟安装
            builder.setProgress(0, 0, true);
            manager.notify(NO_3, builder.build());
            //manager.cancel(NO_3);//设置关闭通知栏
            /*mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    manager.cancel(NO_3);//设置关闭通知栏
                }
            }, 3000);*/
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getChannelId(NotificationManager manager, String channelId, String channelName) {
        //final String channelId = "1";
        //final String channelName = "预警";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);//显示桌面红点
        channel.setLightColor(Color.RED);
        channel.setShowBadge(true);
        manager.createNotificationChannel(channel);
        return channelId;
    }

    public static final String EXTRAS_CHAT_ID = "extras_chat_id";
    private static final int NOTIF_AVCALL_ID = 19833892;
    private static final int NOTIF_SMS_ID = 19833893;
    private static final int NOTIF_CONTSHARE_ID = 19833895;
    private static final int NOTIF_CHAT_ID = 19833896;
    private static final int NOTIF_ONLY_LED = 20170718;

    public void showProgressNotification3(View view) {
        showSMSNotify("showSMSNotify", "NotifyMsg", 2, ChatMessageCategory.ALERT, false);
        //showSMSNotify("showSMSNotify","NotifyMsg",3,ChatMessageCategory.NOTIFICATION_GETUP,false);
        //showSMSNotify("showSMSNotify","NotifyMsg",3,ChatMessageCategory.NOTIFICATION_REPORT,false);

    }

    public void showSMSNotify(String title, String msg, long remoteNumber, int type, boolean isOfflineMsg) {
        /*if (!mStarted) {
            return;
        }*/
        Log.i(TAG, "showSMSNotify: " + title);
        //mNotifManager.cancel(NOTIF_SMS_ID);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRAS_CHAT_ID, remoteNumber);
        Notification notification;

        String channelId = "union_broad_msg";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "msg", NotificationManager.IMPORTANCE_DEFAULT);
            mNotifManager.createNotificationChannel(channel);
        }
        int smallIconId;
        boolean smallResolution = isSmallResolution(NotificationActivity.this);
        Log.i(TAG, "smallResolution: " + smallResolution);
        if (smallResolution) {
            smallIconId = R.mipmap.ic_launcher2;
        } else {
            smallIconId = R.mipmap.ic_launcher1;
        }
        smallIconId = R.drawable.ic_small_icon_24;
        if (type == ChatMessageCategory.ALERT) {
            intent.setClass(NotificationActivity.this, NotificationActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(NotificationActivity.this, NOTIF_SMS_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, channelId)
                    //.setPriority(Notification.PRIORITY_HIGH)
                    //.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    //.setSmallIcon(IconCompat.createWithAdaptiveBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher1)))
                    .setSmallIcon(smallIconId)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher1))
                    .setContentIntent(contentIntent).setAutoCancel(true)
                    .setLights(Color.RED, 100, 100)
                    .setVibrate(new long[]{500L, 200L, 300L, 200L})
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setColor(getResources().getColor(R.color.colorNotification))
                    .setColorized(true);
            if (!isOfflineMsg) {
                /*String uriStr = "android.resource://"
                        + NgnApplication.getContext().getPackageName() + "/"
                        + R.raw.alarm;
                Uri uri = Uri.parse(uriStr);
                builder.setSound(uri);*/
            }
            notification = builder.build();
            mNotifManager.notify(NOTIF_SMS_ID, notification);
        } else if (type == ChatMessageCategory.NOTIFICATION_GETUP) {
            intent.setClass(NotificationActivity.this, NotificationActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(NotificationActivity.this, NOTIF_SMS_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            //cancelMsgNotify();
            notification = new NotificationCompat.Builder(NotificationActivity.this, channelId)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.sms_25)
                    .setContentIntent(contentIntent).setAutoCancel(true)
                    .setLights(Color.GREEN, 500, 500)
                    .setContentTitle(title).setContentText(msg).build();

            //mVibrator = (Vibrator) NgnApplication.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            //mVibrator.vibrate(new long[]{100L, 300L, 100L, 300L}, 0);
            //startNofityVibratorTask(60000);
            //startNotifyGetUptask(120000, 1000000);
            mNotifManager.notify(NOTIF_SMS_ID, notification);
        } else {
            if (type == ChatMessageCategory.NOTIFICATION_REPORT) {
                intent.setClass(NotificationActivity.this, NotificationActivity.class);
            } else {
                intent.setClass(NotificationActivity.this, NotificationActivity.class);
            }
            PendingIntent contentIntent = PendingIntent.getActivity(NotificationActivity.this, NOTIF_SMS_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            boolean isIMSilence = false;/*Engine
                    .getInstance()
                    .getConfigurationService()
                    .getBoolean(
                            NgnConfigurationEntry.IM_SETTING_INCOMING_IM_SILENCE,
                            NgnConfigurationEntry.DEFAULT_IM_SETTING_INCOMING_SILENCE);*/
            if (!isIMSilence) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    String CHANNEL_ID = "union_broad_im";
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "im", NotificationManager.IMPORTANCE_HIGH);
                    channel.enableLights(true);//显示桌面红点
                    channel.setLightColor(Color.RED);
                    channel.setShowBadge(true);
                    mNotifManager.createNotificationChannel(channel);

                    notification = new Notification.Builder(NotificationActivity.this, CHANNEL_ID)
                            //.setPriority(Notification.PRIORITY_DEFAULT)
                            .setVisibility(Notification.VISIBILITY_PRIVATE)
                            .setSmallIcon(R.drawable.sms_25)
                            .setVibrate(new long[]{500L, 200L, 300L, 200L})
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true)
                            .setShowWhen(true)
                            //.setLights(Color.GREEN, 500, 500)
                            //.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                            .setContentTitle(title)
                            .setContentText(msg)
                            .build();

                } else {
                    notification = new NotificationCompat.Builder(
                            NotificationActivity.this, channelId)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setSmallIcon(R.drawable.sms_25)
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true)
                            .setLights(Color.GREEN, 500, 500)
                            .setVibrate(new long[]{500L, 200L, 300L, 200L})
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setContentTitle(title)
                            .setContentText(msg)
                            .build();
                }
                mNotifManager.notify((type == ChatMessageCategory.NOTIFICATION_REPORT)
                        ? -100 : NOTIF_SMS_ID, notification);
            }
        }

        Log.d(TAG, "showSMSNotify number=" + remoteNumber);

    }

    public boolean isSmallResolution(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels * dm.density;
        float height = dm.heightPixels * dm.density;
        return width <= 320 || height <= 480;
    }

}