package com.xxm.review.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.xxm.review.R;

public class TextSwitcherActivity extends AppCompatActivity {
    private TextSwitcher textSwitcher;
    private final String[] strs = new String[]{
            "C++ 多态性 运算符重载",
            "C++ 多态性 虚函数、抽象类（一）",
            "C++ 多态性 虚函数、抽象类（二）",
            "C++ 作用域分辨符"
    };

    private int times = 0;

    private Handler mHandler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            next();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switcher);


        textSwitcher = findViewById(R.id.textSwitcher);

        // 设置转换时的淡入和淡出动画效果（可选）
        Animation in = AnimationUtils.loadAnimation(this, R.anim.push_up_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(TextSwitcherActivity.this);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                tv.setTextColor(Color.MAGENTA);
                return tv;
            }
        });

        next();

    }

    public void next() {
        textSwitcher.setText(strs[times++ % strs.length]);
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

}
