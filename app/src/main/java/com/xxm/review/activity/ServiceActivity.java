package com.xxm.review.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.service.CountService;
import com.xxm.review.service.CountService.MyBinder;

/**
 * service 测试
 */
public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = ServiceActivity.class.getSimpleName();
    private Context mContext;
    private Intent serviceIntent;
    private CountServiceConn serviceConnect;
    private CountService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mContext = ServiceActivity.this;
        serviceIntent = new Intent(mContext,CountService.class);

    }

    /**
     * 开启service
     */
    public void startService(View view){

        startService(serviceIntent);

    }

    /**
     * 停止service
     */
    public void stopService(View view){
        stopService(serviceIntent);
    }

    /**
     * 绑定service
     */
    public void bindService(View view){
        Log.d(TAG,"Service.BIND_AUTO_CREATE= "+Service.BIND_AUTO_CREATE);
        serviceConnect = new CountServiceConn();
        bindService(serviceIntent,serviceConnect, Service.BIND_AUTO_CREATE);
    }

    /**
     * service解除绑定
     * 调用unbindService()的前提条件
     *  1、service 是用bindService()启动的
     *  2、ServiceConnection 对象不为null
     */
    public void unBindService(View view){
        Log.d(TAG,"unBindService");
        if (serviceConnect != null) {
            unbindService(serviceConnect);
            serviceConnect = null;
        }

    }



    class CountServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected --> name"+ name);
            MyBinder mBinder = (MyBinder) service;
            mService = mBinder.getService();
            Log.d(TAG,"Count="+mService.getCount());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceConnected --> name"+ name);
        }
    }

}
