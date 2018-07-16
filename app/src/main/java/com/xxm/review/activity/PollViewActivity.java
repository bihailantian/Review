package com.xxm.review.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxm.review.R;

import java.util.ArrayList;
import java.util.List;

public class PollViewActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private Context mContext;
    private List<TextView> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_view);
        mContext = PollViewActivity.this;
        mViewPager = findViewById(R.id.viewPager);

        initData();

        mViewPager.setAdapter(new myAdapter());

    }

    private void initData() {
        list = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            TextView textView = new TextView(mContext);
            textView.setText("第" + i + "个View");
            textView.setTextColor(Color.RED);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            list.add(textView);
        }
    }


    class myAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            View view = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
            container.addView(list.get(position));

            return list.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(list.get(position));
        }
    }
}
