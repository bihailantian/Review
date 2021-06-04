package com.xxm.review.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxm.review.R;
import com.xxm.review.view.TextViewExpandableAnimation;


/**
 *
 */
public class NormalModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_mode);
        String text = getString(R.string.tips);

        TextViewExpandableAnimation tvExpand = (TextViewExpandableAnimation) findViewById(R.id.tv_expand);
        tvExpand.setText(text + text + text + text);

    }
}
