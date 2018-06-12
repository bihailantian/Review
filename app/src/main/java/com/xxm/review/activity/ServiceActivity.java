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

/**
 * service 测试
 */
public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = ServiceActivity.class.getSimpleName();
    private Context mContext;
    private Intent serviceIntent;
    private CountServiceConn serviceConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mContext = ServiceActivity.this;
        serviceIntent = new Intent(mContext,CountService.class);
        serviceConnect = new CountServiceConn();
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
        bindService(serviceIntent,serviceConnect, Service.BIND_AUTO_CREATE);
    }

    /**
     * service解除绑定
     */
    public void unBindService(View view){
        unbindService(serviceConnect);
    }



    class CountServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected --> name"+ name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceConnected --> name"+ name);
        }
    }

}
