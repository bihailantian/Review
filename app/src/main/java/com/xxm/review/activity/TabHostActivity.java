package com.xxm.review.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.activity.tab.SimpleTab1Activity;
import com.xxm.review.activity.tab.SimpleTab2Activity;
import com.xxm.review.activity.tab.SimpleTab3Activity;
import com.xxm.review.base.BaseActivity;

public class TabHostActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);


        findViewById(R.id.btn_tab1).setOnClickListener(this);
        findViewById(R.id.btn_tab2).setOnClickListener(this);
        findViewById(R.id.btn_tab3).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_tab1:
                intent = new Intent(TabHostActivity.this, SimpleTab1Activity.class);
                break;

            case R.id.btn_tab2:
                intent = new Intent(TabHostActivity.this, SimpleTab2Activity.class);
                break;

            case R.id.btn_tab3:
                intent = new Intent(TabHostActivity.this, SimpleTab3Activity.class);
                break;
        }

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
