package com.xxm.review.activity;

import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void showProgressNotification(View view) {
        final Builder builder;
        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Builder(this, getChannelId(manager, CHANNEL_ID_WARN, "预警"));
        } else {
            builder = new Builder(this);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);
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


}