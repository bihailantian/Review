package com.xxm.review;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xxm.review.activity.BadgeActivity;
import com.xxm.review.activity.CalendarActivity;
import com.xxm.review.activity.ConfigActivity;
import com.xxm.review.activity.ConstraintLayoutActivity;
import com.xxm.review.activity.GridViewActivity;
import com.xxm.review.activity.ImageActivity;
import com.xxm.review.activity.LifeActivity;
import com.xxm.review.activity.ListActivity;
import com.xxm.review.activity.OkHttp3Activity;
import com.xxm.review.activity.OpenGLActivity;
import com.xxm.review.activity.PlaceholderActivity;
import com.xxm.review.activity.PollViewActivity;
import com.xxm.review.activity.ProgressBarActivity;
import com.xxm.review.activity.RecyclerViewActivity;
import com.xxm.review.activity.RetrofitActivity;
import com.xxm.review.activity.ServiceActivity;
import com.xxm.review.activity.ShapeActivity;
import com.xxm.review.activity.SharePreActivity;
import com.xxm.review.activity.TabHostActivity;
import com.xxm.review.activity.TableWithBorderActivity;
import com.xxm.review.activity.VideoActivity;
import com.xxm.review.activity.VirtualApkActivity;
import com.xxm.review.activity.animation.AnimationActivity;
import com.xxm.review.domain.Item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Activity mActivity;
    private List<Item> itemList = new ArrayList<>();

    {
        //itemList.add(new Item("AsyncTaskActivity", "AsyncTaskActivity", AsyncTaskActivity.class));
        itemList.add(new Item("动画", "动画", AnimationActivity.class));
        itemList.add(new Item("GridView", "GridView添加分割线", GridViewActivity.class));
        itemList.add(new Item("占位符", "占位符的使用", PlaceholderActivity.class));
        itemList.add(new Item("OpenGL", "android 3D", OpenGLActivity.class));
        itemList.add(new Item("TabHost", "使用TabHost的3中方式", TabHostActivity.class));
        itemList.add(new Item("RecyclerView", "RecyclerView", RecyclerViewActivity.class));
        itemList.add(new Item("视频播放", "视频播放", VideoActivity.class));
        itemList.add(new Item("ListActivity", "ListActivity", ListActivity.class));
        itemList.add(new Item("表格添加边框", "TableLayout添加边框", TableWithBorderActivity.class));
        itemList.add(new Item("ProgressBar", "ProgressBar", ProgressBarActivity.class));
        itemList.add(new Item("SharePreUtils", "SharePreUtils", SharePreActivity.class));
        itemList.add(new Item("图标badge", "给图标设置徽章", BadgeActivity.class));
        itemList.add(new Item("图片处理", "缩放法压缩、RGB_565", ImageActivity.class));
        itemList.add(new Item("VirtualApk", "滴滴的VirtualApk插件化框架使用", VirtualApkActivity.class));
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
        mActivity = this;

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ListAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = itemList.get(position);
                Intent intent = new Intent(mActivity, item.getClazz());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });


        Log.d(TAG, "getPackageName()= " + getPackageName());
        Log.d(TAG, "getApplicationInfo().packageName= " + getApplicationInfo().packageName);
        Log.d(TAG, "getApplicationInfo().processName= " + getApplicationInfo().processName);
        Log.d(TAG, "getApplication().getPackageName()= " + getApplication().getPackageName());
        Log.d(TAG, "目录= " + Environment.getExternalStorageDirectory().getAbsolutePath());
        Log.d(TAG, "sd卡: " + Environment.getExternalStorageState());
        Log.d(TAG, "sd卡: " + Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
        Log.d(TAG, "文件路径: " + Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Review");
        Log.d(TAG, "文件路径: " + Environment.getExternalStorageDirectory());
        Log.d(TAG, "Build.VERSION.SDK_INT= " + Build.VERSION.SDK_INT);
        try {
            Log.d(TAG, "getApplicationInfo().packageName= " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


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
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_main, null);
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
