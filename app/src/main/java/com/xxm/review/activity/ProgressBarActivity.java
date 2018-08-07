package com.xxm.review.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.xxm.review.R;

public class ProgressBarActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        btnStart = findViewById(R.id.btn_start);
        progressBar = findViewById(R.id.progressBar);
        btnStart.setOnClickListener(this);


    }

    private void setAnimation(final ProgressBar view, final int progress) {
        ValueAnimator animator = ValueAnimator.ofInt(0, progress).setDuration(1500);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setProgress((int) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
//        progressBar.setProgress(randomNum());

        setAnimation(progressBar, randomNum());
    }

    private int randomNum() {
        return (int) (Math.random() * 100);
    }
}
