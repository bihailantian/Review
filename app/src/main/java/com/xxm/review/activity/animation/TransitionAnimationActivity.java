package com.xxm.review.activity.animation;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xxm.review.R;

public class TransitionAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TransitionAnimationAct";
    FloatingActionButton fab;
    Intent intent;
    private View content;//根布局对象（用来控制整个布局）
    private View mPuppet0;//揭露层对象
    private int centerX;
    private int centerY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_animation);
        Log.i(TAG, "onCreate");
        content = findViewById(R.id.reveal_content0);
        mPuppet0 = findViewById(R.id.view_puppet);
        intent = new Intent(this, NextTransitionAnimationActivity.class);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int[] vLocation = new int[2];
        fab.getLocationInWindow(vLocation);
        centerX = vLocation[0] + fab.getMeasuredWidth() / 2;
        centerY = vLocation[1] + fab.getMeasuredHeight() / 2;
        intent.putExtra("cx", centerX);
        intent.putExtra("cy", centerY);
        startActivity(intent);
    }

    private Animator createRevealAnimator(int x, int y) {
        float startRadius = (float) Math.hypot(content.getHeight(), content.getWidth());
        float endRadius = fab.getMeasuredWidth() / 2;

        //注意揭露动画开启时是用根布局作为操作对象，关闭时用揭露层作为操作对象
        Animator animator = ViewAnimationUtils.createCircularReveal(mPuppet0, x, y, startRadius, endRadius);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());//设置插值器
        animator.addListener(animatorListener0);
        return animator;
    }

    //定义动画状态监听器_按下返回键版
    private Animator.AnimatorListener animatorListener0 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            mPuppet0.setVisibility(View.VISIBLE);//按下返回键时，动画开启，揭露层设置为可见
            fab.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mPuppet0.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    //第二个活动退回来时，回调本方法
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
        //动画需要依赖于某个视图才可启动，
        // 这里依赖于根布局对象，并且开辟一个子线程，充分利用资源
        content.post(new Runnable() {
            @Override
            public void run() {
                int[] vLocation = new int[2];
                fab.getLocationInWindow(vLocation);
                centerX = vLocation[0] + fab.getMeasuredWidth() / 2;
                centerY = vLocation[1] + fab.getMeasuredHeight() / 2;
                Animator animator = createRevealAnimator(centerX, centerY);
                animator.start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}