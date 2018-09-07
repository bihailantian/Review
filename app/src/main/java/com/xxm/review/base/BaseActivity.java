package com.xxm.review.base;

import android.support.v7.app.AppCompatActivity;

import com.xxm.review.R;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
