package com.xxm.review.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.domain.InfoConfig;

/**
 * 连续设置属性
 */
public class ConfigActivity extends BaseActivity {

    private static final String TGA = ConfigActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        //连续设置属性
        InfoConfig.Builder builder = new InfoConfig.Builder()
                .setName("TEST")
                .setAge(10)
                .setAddress("address");


        test("1234456789");
        test("123");

    }


    private void test(@NonNull String name) {
        Log.d(TGA, name);
    }

}
