package com.xxm.review.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xxm.review.R;
import com.xxm.review.utils.DeviceIdUtil;

public class DeviceIdActivity extends AppCompatActivity {

    private static final String TAG = "DeviceIdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);

        TextView textView = findViewById(R.id.textView);

        StringBuilder builder = new StringBuilder();

        String deviceId = DeviceIdUtil.getDeviceId(this);
        Log.d(TAG, "deviceId=" + deviceId + ", length=" + deviceId.length());

        builder.append("deviceId=" + deviceId + ",\n\n length=" + deviceId.length());

        textView.setText(builder.toString());


    }
}