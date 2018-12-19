package com.xxm.review.activity;

import android.os.Bundle;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

/**
 * JobService和JobScheduler测试
 */
public class ServerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
    }

    public void startJobSchedulerService(View view) {

    }
}
