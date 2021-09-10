package com.xxm.review.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;

import com.xxm.review.R;

public class SharedElementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView(savedInstanceState);
        setContentView(R.layout.activity_shared_element);
    }

    /**
     *在设置布局之前
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void beforeSetContentView(Bundle savedInstanceState) {
        // 允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

    }
}
