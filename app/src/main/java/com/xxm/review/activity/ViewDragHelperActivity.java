package com.xxm.review.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xxm.review.R;


public class ViewDragHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drag_helper);

        findViewById(R.id.tv_1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(ViewDragHelperActivity.this, "长按了VIewDarg1", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        findViewById(R.id.tv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDragHelperActivity.this, "点击了VIewDarg1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
