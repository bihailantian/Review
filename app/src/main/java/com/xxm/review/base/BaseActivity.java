package com.xxm.review.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxm.review.R;

public abstract class BaseActivity extends AppCompatActivity {


    protected BaseActivity mActivity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    /**
     * activity跳转
     *
     * @param clazz activity的class
     */
    protected void actIntent(Class clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * activity跳转
     *
     * @param clazz activity的class
     */
    protected void actIntent(Class clazz, String name, Parcelable value) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtra(name, value);
        startActivity(intent);
    }


}
