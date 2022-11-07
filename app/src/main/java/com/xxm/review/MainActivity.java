package com.xxm.review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xxm.review.activity.AdaptationFontActivity;
import com.xxm.review.activity.BadgeActivity;
import com.xxm.review.activity.CalendarActivity;
import com.xxm.review.activity.ConfigActivity;
import com.xxm.review.activity.ConstraintLayoutActivity;
import com.xxm.review.activity.GridViewActivity;
import com.xxm.review.activity.ImageActivity;
import com.xxm.review.activity.ImageInfoActivity;
import com.xxm.review.activity.JobServiceActivity;
import com.xxm.review.activity.LifeActivity;
import com.xxm.review.activity.ListActivity;
import com.xxm.review.activity.MotionLayoutActivity;
import com.xxm.review.activity.NetChangeListenerActivity;
import com.xxm.review.activity.NormalModeActivity;
import com.xxm.review.activity.NotificationActivity;
import com.xxm.review.activity.OkHttp3Activity;
import com.xxm.review.activity.OpenGLActivity;
import com.xxm.review.activity.PlaceholderActivity;
import com.xxm.review.activity.PollViewActivity;
import com.xxm.review.activity.PopupMenuActivity;
import com.xxm.review.activity.ProgressBarActivity;
import com.xxm.review.activity.RecyclerViewActivity;
import com.xxm.review.activity.RetrofitActivity;
import com.xxm.review.activity.RotateDrawableActivity;
import com.xxm.review.activity.ServiceActivity;
import com.xxm.review.activity.ShapeActivity;
import com.xxm.review.activity.SharePreActivity;
import com.xxm.review.activity.SimpleToolsTipsActivity;
import com.xxm.review.activity.TabHostActivity;
import com.xxm.review.activity.TableWithBorderActivity;
import com.xxm.review.activity.TextSwitcherActivity;
import com.xxm.review.activity.VectorDrawableToImageActivity;
import com.xxm.review.activity.VideoActivity;
import com.xxm.review.activity.ViewDragHelperActivity;
import com.xxm.review.activity.ViewTipsActivity;
import com.xxm.review.activity.VirtualApkActivity;
import com.xxm.review.activity.animation.AnimationActivity;
import com.xxm.review.activity.custom.CustomViewActivity;
import com.xxm.review.activity.ui.CalculatorActivity;
import com.xxm.review.domain.Item;
import com.xxm.review.myflow.FlowActivity;
import com.xxm.review.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Activity mActivity;
    private final List<Item> itemList = new ArrayList<>();
    private ListAdapter adapter;

    {
        //itemList.add(new Item("AsyncTaskActivity", "AsyncTaskActivity", AsyncTaskActivity.class));
        itemList.add(new Item("VectorDrawable To Image", "Android Vector转成png、jpg图片", VectorDrawableToImageActivity.class));
        itemList.add(new Item("Simple Tools Tips", "Android  SimpleToolsTips", SimpleToolsTipsActivity.class));
        itemList.add(new Item("Kotlin 之 flow", "Android 上的 Kotlin flow 数据流", FlowActivity.class));
        itemList.add(new Item("网络变化监测", "网络变化监测", NetChangeListenerActivity.class));
        itemList.add(new Item("MotionLayout 动画", "MotionLayout 动画", MotionLayoutActivity.class));
        itemList.add(new Item("字体大小适配", "字体大小适配", AdaptationFontActivity.class));
        itemList.add(new Item("图片信息", "获取一张图片的属性信息", ImageInfoActivity.class));
        itemList.add(new Item("ViewDragHelper", "ViewDragHelper一般用在一个自定义ViewGroup的内部，ViewDragHelper的实例是通过静态工厂方法创建的", ViewDragHelperActivity.class));
        itemList.add(new Item("Notification", "Notification状态栏通知", NotificationActivity.class));
        itemList.add(new Item("按行数进行折叠带过渡动画的TextView", "按行数进行折叠带过渡动画的TextView", NormalModeActivity.class));
        itemList.add(new Item("根据Span修改Text外观或者尺寸", "根据Span修改Text外观或者尺寸", TextSpanActivity.class));
        itemList.add(new Item("RotateDrawable", "用RotateDrawable实现网易云音乐唱片机效果", RotateDrawableActivity.class));
        itemList.add(new Item("CustomView", "自定义View", CustomViewActivity.class));
        itemList.add(new Item("TextSwitcher", "通知公告TextSwitcher自动上下滚动带点击事件", TextSwitcherActivity.class));
        itemList.add(new Item("ViewTooltip", "ViewTooltip例子", ViewTipsActivity.class));
        itemList.add(new Item("PopupMenu", "PopupMenu例子", PopupMenuActivity.class));
        itemList.add(new Item("UI界面", "简约计算器界面", CalculatorActivity.class));
        itemList.add(new Item("JobService和JobScheduler测试", "JobService和JobScheduler测试", JobServiceActivity.class));
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
        if (adapter == null) {
            adapter = new ListAdapter();
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Item item = itemList.get(position);
            Intent intent = new Intent(mActivity, item.getClazz());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });

        PathPrint.printPath(this);

        Log.d(TAG, "IP: " + NetUtils.getIpAddress());

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
