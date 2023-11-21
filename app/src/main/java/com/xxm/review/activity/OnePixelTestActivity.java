package com.xxm.review.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.xxm.review.R;

public class OnePixelTestActivity extends Activity {

    private BootCompleteReceiver bootCompleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pixel_test);

        findViewById(R.id.btn_open).setOnClickListener(v -> OnePixelActivity.startKeepLivePage());

        findViewById(R.id.btn_close).setOnClickListener(v -> OnePixelActivity.killKeepLivePage());

        registerBootCompleteReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBootCompleteReceiver();
    }

    private void registerBootCompleteReceiver() {
        if (bootCompleteReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            bootCompleteReceiver = new BootCompleteReceiver();
            registerReceiver(bootCompleteReceiver, intentFilter);
        }
    }

    private void unregisterBootCompleteReceiver() {
        if (bootCompleteReceiver != null) {
            unregisterReceiver(bootCompleteReceiver);
            bootCompleteReceiver = null;
        }
    }


    public class BootCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                OnePixelActivity.killKeepLivePage();
            } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                OnePixelActivity.startKeepLivePage();
            }
        }
    }
}