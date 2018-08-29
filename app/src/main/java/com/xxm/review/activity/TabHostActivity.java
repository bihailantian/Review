package com.xxm.review.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TabHost;

import com.xxm.review.R;

public class TabHostActivity extends FragmentActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        tabHost = findViewById(R.id.tabHost);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
