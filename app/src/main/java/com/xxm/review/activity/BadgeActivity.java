package com.xxm.review.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.view.BadgeView;


public class BadgeActivity extends BaseActivity implements View.OnClickListener {

    private BadgeView mBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        ImageView ivBadge = findViewById(R.id.iv_badge);
        ivBadge.setOnClickListener(this);
        mBadge = new BadgeView(this, ivBadge);

        mBadge.setText("1");
        mBadge.show();
        mBadge.setPadding(5, 0, 0, 0);

    }

    @Override
    public void onClick(View v) {
        if (mBadge.isShown()) {
            mBadge.hide(true);
        } else {
            mBadge.setText("1");
            mBadge.show(true);
        }
    }
}
