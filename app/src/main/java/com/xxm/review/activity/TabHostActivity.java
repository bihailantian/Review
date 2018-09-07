package com.xxm.review.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.xxm.review.R;

public class TabHostActivity extends FragmentActivity {


    /**
     * Tab选项卡的图标
     */
    public static int mImageViewArray[] = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    /**
     * Tab选项卡的文字
     */
    public static String mTextViewArray[] = {"主页", "关于", "设置", "搜索", "更多"};


    private TabHost mTabHost;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
//        mTabHost = findViewById(R.id.tabHost);

        initView();
    }

    private void initView() {
        // 实例化布局对象
        mInflater = LayoutInflater.from(this);

        View tab_widget_item1 = LayoutInflater.from(this).inflate(R.layout.item_tab_view, null);
        View tab_widget_item2 = LayoutInflater.from(this).inflate(R.layout.item_tab_view, null);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
