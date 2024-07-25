package com.xxm.review;

import static android.os.Environment.getExternalStorageDirectory;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.didi.virtualapk.PluginManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xxm.review.utils.ALog;

import java.io.File;


public class ReviewApplication extends Application {

    private static final String TAG = ReviewApplication.class.getSimpleName();

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ALog.d(TAG, "attachBaseContext");
        PluginManager.getInstance(base).init();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ALog.d(TAG, "onCreate");
        PluginManager pluginManager = PluginManager.getInstance(this);
        //此处是当查看插件apk是否存在,如果存在就去加载(比如修改线上的bug,把插件apk下载到sdcard的根目录下取名为 reviewPlugin.apk)
        File apk = new File(getExternalStorageDirectory(), "reviewPlugin.apk");
        if (apk.exists()) {
            try {
                Log.i(TAG, "准备加载...");
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //若是在项目上线之后，隐藏日志就要实现：
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
