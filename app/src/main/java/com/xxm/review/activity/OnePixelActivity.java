package com.xxm.review.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.xxm.review.ReviewApplication;

/**
 * 1像素页面保活
 */
public class OnePixelActivity extends AppCompatActivity {
    private static final String TAG = "OnePixelActivity-";
    private static OnePixelActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "==onCreate==");
        instance = this;
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    public static void startKeepLivePage() {
        Intent intent = new Intent(ReviewApplication.getContext(), OnePixelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ReviewApplication.getContext().startActivity(intent);
    }

    public static void killKeepLivePage() {
        if (instance != null) {
            instance.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "==onDestroy==");
        instance = null;
    }


}