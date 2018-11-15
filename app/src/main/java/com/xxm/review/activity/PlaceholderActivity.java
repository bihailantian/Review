package com.xxm.review.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

/**
 * 占位符
 */
public class PlaceholderActivity extends BaseActivity {

    private TextView tvCount;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        tvCount = findViewById(R.id.tv_count);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnReduce = findViewById(R.id.btn_reduce);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tvCount.setText(getString(R.string.placeholder_count, count));
            }
        });

        btnReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                tvCount.setText(getString(R.string.placeholder_count, count));
            }
        });

        tvCount.setText(getString(R.string.placeholder_count, count));
    }
}
