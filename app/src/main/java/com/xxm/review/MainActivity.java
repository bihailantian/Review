package com.xxm.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xxm.review.activity.CalendarActivity;
import com.xxm.review.activity.ConfigActivity;
import com.xxm.review.activity.ConstraintLayoutActivity;
import com.xxm.review.activity.LifeActivity;
import com.xxm.review.activity.OkHttp3Activity;
import com.xxm.review.activity.PollViewActivity;
import com.xxm.review.activity.RetrofitActivity;
import com.xxm.review.activity.ServiceActivity;
import com.xxm.review.activity.ShapeActivity;
import com.xxm.review.domain.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;

    private Context mContext;
    private List<Item> itemList = new ArrayList<>();

    {
        itemList.add(new Item("ViewPager", "ViewPager的使用", PollViewActivity.class));
        itemList.add(new Item("Shape", "Shape 资源的定义和使用", ShapeActivity.class));
        itemList.add(new Item("Calendar", "Calendar 日期选择", CalendarActivity.class));
        itemList.add(new Item("OkHttp3", "OkHttp3 源码学习", OkHttp3Activity.class));
        itemList.add(new Item("Retrofit", "Retrofit 框架测试", RetrofitActivity.class));
        itemList.add(new Item("ConfigActivity", "连续设置属性", ConfigActivity.class));
        itemList.add(new Item("Service", "startService()和bindService()两种方式启动service", ServiceActivity.class));
        itemList.add(new Item("Activity的生命周期", "Activity的生命周期", LifeActivity.class));
        itemList.add(new Item("ConstraintLayout", "ConstraintLayout 布局", ConstraintLayoutActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ListAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = itemList.get(position);
                Intent intent = new Intent(mContext, item.getClazz());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });


        Log.d(TAG, "getPackageName()= " + getPackageName());
        Log.d(TAG, "getApplicationInfo().packageName= " + getApplicationInfo().packageName);
        Log.d(TAG, "getApplicationInfo().processName= " + getApplicationInfo().processName);
        Log.d(TAG, "getApplication().getPackageName()= " + getApplication().getPackageName());

    }


    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Item getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.list_item_main, null);
                holder = new ViewHolder();
                holder.tvTitle = convertView.findViewById(R.id.tv_title);
                holder.tvDesc = convertView.findViewById(R.id.tv_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Item item = getItem(position);
            holder.tvTitle.setText(item.getTitle());
            holder.tvDesc.setText(item.getDesc());

            return convertView;
        }


        class ViewHolder {
            TextView tvTitle;
            TextView tvDesc;
        }

    }

}
