package com.xxm.review.activity.animation;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.xxm.review.R;

public class NextTransitionAnimationActivity extends AppCompatActivity {

    private static final String TAG = "NextTransitionAnimation";
    private View content;//根布局对象（用来控制整个布局）
    private View mPuppet;//揭露层对象
    private int mX;
    private int mY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_transition_animation);//先加载好整个布局，后面再用整个布局作为揭露动画的操作对象，揭露完毕后再去掉揭露层
        Log.i(TAG, "onCreate");
        content = findViewById(R.id.reveal_content);
        mPuppet = findViewById(R.id.view_puppet);

        //动画需要依赖于某个视图才可启动，
        // 这里依赖于根布局对象，并且开辟一个子线程，充分利用资源
        content.post(new Runnable() {
            @Override
            public void run() {
                mX = getIntent().getIntExtra("cx", 0);
                mY = getIntent().getIntExtra("cy", 0);
                Animator animator = createRevealAnimator(mX, mY);
                animator.start();
            }
        });
    }

    private Animator createRevealAnimator(int x, int y) {
        float startRadius = 0;
        float endRadius = (float) Math.hypot(content.getHeight(), content.getWidth());

        Animator animator = ViewAnimationUtils.createCircularReveal(
                content, x, y,
                startRadius,
                endRadius);
        animator.setDuration(660);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        //判断标志位reversed，true则为添加返回键版动画监听器，false则为跳转动画开启版
        // if (!reversed)
        animator.addListener(animatorListener1);
        return animator;
    }

    //定义动画状态监听器_跳转动画开启版
    private Animator.AnimatorListener animatorListener1 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
//            content.setVisibility(View.VISIBLE);//跳转进来时，（因为finish之前会将之设置为不可见，）
            // 根布局要设置为可见，与finish部分的不可见相对应
//            mPuppet.setAlpha(1);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mPuppet.startAnimation(createAlphaAnimation());
            mPuppet.setVisibility(View.INVISIBLE);//动画结束时，揭露动画设置为不可见
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    private AlphaAnimation createAlphaAnimation() {
        AlphaAnimation aa = new AlphaAnimation(1, 0);
        aa.setDuration(400);
        aa.setInterpolator(new AccelerateDecelerateInterpolator());//设置插值器
        return aa;
    }
}