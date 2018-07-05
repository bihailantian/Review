package com.xxm.review.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxm.review.R;
import com.xxm.review.domain.InfoConfig;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        //连续设置属性
        InfoConfig.Builder builder = new InfoConfig.Builder()
                .setName("TEST")
                .setAge(10)
                .setAddress("address");

    }

}
