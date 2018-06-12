package com.xxm.review.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CountService extends Service {

    private static final String TAG = CountService.class.getSimpleName();

    private MyBinder mBinder = new MyBinder();
    private int count = 1;

    public CountService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
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
    }


    public int getCount() {
        return count;
    }


    public class MyBinder extends Binder {

        public CountService getService() {
            return CountService.this;
        }

    }
}
