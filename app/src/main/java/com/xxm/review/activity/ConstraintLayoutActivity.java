package com.xxm.review.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxm.review.R;

/**
 * ConstraintLayout 布局
 */
public class ConstraintLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
