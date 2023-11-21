package com.xxm.review.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.utils.SharePrefUtil;

import java.util.Map;

public class SharePreActivity extends BaseActivity implements View.OnClickListener {


    private static final String SHARE_PRE_KEY = "SharePreActivity";
    private static final String TAG = "SharePreActivity-";
    private Switch swSharePre;
    private Button btnSharePre;
    private TextView tvSharePre;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_pre);
        mContext = this;

        swSharePre = findViewById(R.id.sw_sharePre);
        btnSharePre = findViewById(R.id.btn_sharePre);
        tvSharePre = findViewById(R.id.tv_sharePre);
        findViewById(R.id.btn_read_sharePre).setOnClickListener(this);

        swSharePre.setOnClickListener(this);
        btnSharePre.setOnClickListener(this);

        swSharePre.setChecked(SharePrefUtil.getBoolean(mContext, SHARE_PRE_KEY, false));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sw_sharePre:
                SharePrefUtil.setBoolean(mContext, SHARE_PRE_KEY, swSharePre.isChecked());
                tvSharePre.setText("sw_sharePre:" + SharePrefUtil.getBoolean(mContext, SHARE_PRE_KEY, false));

                break;
            case R.id.btn_sharePre:
                SharePrefUtil.putString(mContext, "key1-" + System.currentTimeMillis(), "value1-" + System.currentTimeMillis());
                SharePrefUtil.putString(mContext, "key2-" + System.currentTimeMillis(), "value2-" + System.currentTimeMillis());
                SharePrefUtil.putString(mContext, "key3-" + System.currentTimeMillis(), "value2-" + System.currentTimeMillis());
                break;
            case R.id.btn_read_sharePre:
                SharedPreferences preferences = SharePrefUtil.getSharedPreferences(mContext);
                Map<String, String> preferencesAll = (Map<String, String>) preferences.getAll();
                for (Map.Entry<String, String> entry : preferencesAll.entrySet()) {
                    Log.d(TAG, "key:" + entry.getKey() + ", value:" + entry.getValue());
                }
                break;
        }
    }
}
