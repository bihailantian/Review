package com.xxm.review.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.xxm.review.R;

/**
 * 网络变化监测
 */
public class NetChangeListenerActivity extends AppCompatActivity {

    private static final String TAG = NetChangeListenerActivity.class.getName();
    private TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_change_listener);
        checkPermission();
        regBoradCast(true);
        tip = findViewById(R.id.tip);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        regBoradCast(false);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.READ_PHONE_STATE};
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 200);
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == 200) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    private void regBoradCast(boolean bReg) {
        if (bReg) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            filter.addAction("android.net.wifi.RSSI_CHANGED");
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            filter.addAction("android.net.wifi.STATE_CHANGE");
            registerReceiver(m_receiver, filter);
        } else {
            unregisterReceiver(m_receiver);
        }
    }

    private BroadcastReceiver m_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sAction = intent.getAction();
            if (TextUtils.isEmpty(sAction)) {
                return;
            }

            if ("android.net.conn.CONNECTIVITY_CHANGE".compareTo(sAction) == 0
                    || "android.net.wifi.RSSI_CHANGED".compareTo(sAction) == 0
                    || "android.net.wifi.WIFI_STATE_CHANGED".compareTo(sAction) == 0
                    || "android.net.wifi.STATE_CHANGE".compareTo(sAction) == 0) {

                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    // 如果当前的网络连接成功并且网络连接可用
                    if (info.getState() == NetworkInfo.State.CONNECTED
                            && info.isAvailable()) {
                        Log.e(TAG, "NetworkState.Connect 当前使用的网络可用");
                        tip.append("当前使用的网络可用\n");
                        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                            Log.e(TAG, "NetworkState.Connect 当前使用的网络是WIFI");
                            //executePingCmd(spellPing("192.168.101.251"));
                            handler.removeMessages(0x01);
                            handler.sendEmptyMessageDelayed(0x01,200);
                            tip.append("当前使用的网络是WIFI\n");
                        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            if (telephonyManager == null) {
                                Log.e(TAG, "System error, check TELEPHONY_SERVICE.");
                                tip.append("System error, check TELEPHONY_SERVICE\n");
                                return;
                            }
                            if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_READY) {
                                Log.e(TAG, "SimCard is not ready.");
                                tip.append("SimCard is not ready.\n");
                                return;
                            }

                            @SuppressLint("MissingPermission") int networkType = telephonyManager.getNetworkType();
                            if (networkType == TelephonyManager.NETWORK_TYPE_LTE) {
                                Log.e(TAG, "NetworkState.Connect 当前使用的是4G移动网络");
                                tip.append("当前使用的是4G移动网络\n");

                                //executePingCmd(spellPing("192.168.101.251"));
                                handler.removeMessages(0x01);
                                handler.sendEmptyMessageDelayed(0x01,200);
                            } else {
                                Log.e(TAG, "NetworkState.Connect 当前使用的是3G/2G移动网络");
                                tip.append("当前使用的是3G/2G移动网络\n");
                            }
                        }
                    } else {
                        Log.e(TAG, "NetworkState.Disconnect 网络断开");
                        tip.append("NetworkState.Disconnect 网络断开.\n");
                        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                            Log.d(TAG, "WIFI is Disconnectted");
                            tip.append("NetworkStateChange : WIFI is Disconnectted.\n");
                        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            Log.d(TAG, "NetworkStateChange : 4G is Disconnectted");
                            tip.append("4G is Disconnectted.\n");
                        }
                    }
                }
            }
        }
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    Log.e(TAG, "NetworkState.Connect 当前使用的是3G/2G移动网络   0x01 ");
                    new Thread(new PingIpThread()).start();
                    handler.removeMessages(0x01);
                    handler.sendEmptyMessageDelayed(0x01,10000);
                    break;
                case 0x02:
                    tip.append("PING IP 成功\n");
                    break;
                case 0x03:
                    tip.append("PING IP 失败\n");
                    break;
            }

            return false;
        }
    });

    private class PingIpThread implements Runnable {
        private boolean isRunning = true;
        private String pingIp = "www.baidu.com";
        private int timeOut = 3;

        public void setRunning(boolean running) {
            isRunning = running;
        }

        public void setPingIp(String pingIp) {
            this.pingIp = pingIp;
        }

        public void setTimeOut(int timeOut) {
            this.timeOut = timeOut;
        }

        @Override
        public void run() {
            Runtime runtime = Runtime.getRuntime();
            Log.e(TAG, "NetworkState.Connect 当前使用的是3G/2G移动网络" + isRunning + ", timeOut=" + timeOut);
            while (isRunning) {
                try {
                    //ping -c 3 -w 100  中，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
                    Process p = runtime.exec("ping -c 3 -w " + timeOut + " " + pingIp);
                    int ret = p.waitFor();
                    Log.d(TAG, "" + ret);
                    if (ret == 0) {
                        handler.sendEmptyMessage(0x02);
                        //成功
                    } else {
                        handler.sendEmptyMessage(0x03);
                        //ping ip 失败，可能为 1 或者 2
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "Exception：" + e.getMessage());
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "运行了");
            }
        }
    }
}