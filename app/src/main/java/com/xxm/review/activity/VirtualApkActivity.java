package com.xxm.review.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.xxm.review.R;

/**
 * 滴滴的VirtualApk插件化框架使用
 */
public class VirtualApkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_apk);
    }


    public void test(View view) {
        if (PluginManager.getInstance(this).getLoadedPlugin("com.xxm.plugindemo") == null) {
            Toast.makeText(this, "plugin  not loaded", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.setClassName("com.xxm.plugindemo", "com.xxm.plugindemo.PluginMainActivity");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
