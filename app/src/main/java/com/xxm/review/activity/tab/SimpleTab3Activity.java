package com.xxm.review.activity.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

public class SimpleTab3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab3);

        //使用findViewById()来获取TabHost，需要在tabHost.addTab()之前调用 tabHost.setup();否则会报空指针
        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        LayoutInflater.from(this).inflate(R.layout.tab1, tabHost.getTabContentView());
        LayoutInflater.from(this).inflate(R.layout.tab2, tabHost.getTabContentView());
        LayoutInflater.from(this).inflate(R.layout.tab3, tabHost.getTabContentView());

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("标签页一").setContent(R.id.tab01));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("标签页二").setContent(R.id.tab02));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("标签页三").setContent(R.id.tab03));

        //标签切换事件处理，setOnTabChangedListener
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) { //第一个标签
                    Toast.makeText(SimpleTab3Activity.this, "点击标签页一", Toast.LENGTH_SHORT).show();
                }
                if (tabId.equals("tab2")) { //第二个标签
                    Toast.makeText(SimpleTab3Activity.this, "点击标签页二", Toast.LENGTH_SHORT).show();
                }
                if (tabId.equals("tab3")) { //第三个标签
                    Toast.makeText(SimpleTab3Activity.this, "点击标签页三", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
