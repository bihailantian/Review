package com.xxm.review.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.utils.SharePrefUtil;

public class SharePreActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String SHARE_PRE_KEY = "SharePreActivity";
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
                break;
        }
    }
}
