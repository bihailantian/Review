package com.xxm.review.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Locale;

public class CountService extends Service {

    private static final String TAG = CountService.class.getSimpleName();

    private MyBinder mBinder = new MyBinder();
    private int count = 1;
    private LanguageChangeReceiver languageChangeReceiver;

    public CountService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        languageChangeReceiver = new LanguageChangeReceiver();
        registerReceiver(languageChangeReceiver, intentFilter);
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (languageChangeReceiver != null) {
            unregisterReceiver(languageChangeReceiver);
            languageChangeReceiver = null;
        }
    }


    public int getCount() {
        return count;
    }


    public class MyBinder extends Binder {

        public CountService getService() {
            return CountService.this;
        }

    }

    // 接收器实现
    public class LanguageChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
                Locale currentLocale = context.getResources().getConfiguration().locale;
                // 处理语言变化
                Log.d(TAG, "LanguageChangeReceiver=" + currentLocale.getLanguage());
            }
        }
    }
}
