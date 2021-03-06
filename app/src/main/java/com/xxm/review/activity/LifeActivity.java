package com.xxm.review.activity;

import android.os.Bundle;
import android.util.Log;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

/**
 * Activity 的生命周期
 */
public class LifeActivity extends BaseActivity {

    /**
     * onCreate() onStart() onResume() onPause() onStop()  onRestart() onDestroy()
     */

    private static final String TAG = LifeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        Log.d(TAG, "onCreate");

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
