package com.xxm.review.activity.tab;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;

import com.xxm.review.R;

public class SimpleTab1Activity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取该Activity里面的TabHost组件
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_simple_tab1, tabHost.getTabContentView(), true);

        //setContentView(R.layout.activity_simple_tab1);

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("标签页一").setContent(R.id.tab01));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("标签页二").setContent(R.id.tab02));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("标签页三").setContent(R.id.tab03));

        //标签切换事件处理，setOnTabChangedListener
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) { //第一个标签
                    Toast.makeText(SimpleTab1Activity.this, "点击标签页一", Toast.LENGTH_SHORT).show();
                }
                if (tabId.equals("tab2")) { //第二个标签
                    Toast.makeText(SimpleTab1Activity.this, "点击标签页二", Toast.LENGTH_SHORT).show();
                }
                if (tabId.equals("tab3")) { //第三个标签
                    Toast.makeText(SimpleTab1Activity.this, "点击标签页三", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
