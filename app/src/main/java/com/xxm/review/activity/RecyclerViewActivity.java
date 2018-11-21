package com.xxm.review.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {


    private static final String TAG = "RecyclerViewActivity";
    private RecyclerView mRecyclerView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = findViewById(R.id.recyclerView);

        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item " + i);
        }
        MyAdapter adapter = new MyAdapter();
        gridManager();
        //添加分割线
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //重写onCreateOptionMenu(Menu menu)方法，当菜单第一次被加载时调用
        getMenuInflater().inflate(R.menu.menu_recyclerview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grid:
                Toast.makeText(mActivity, R.string.grid, Toast.LENGTH_SHORT).show();
                gridManager();
                break;

            case R.id.linear:
                Toast.makeText(mActivity, R.string.linear, Toast.LENGTH_SHORT).show();
                linearManager();
                break;

            case R.id.staggeredGrid:
                Toast.makeText(mActivity, R.string.staggeredGrid, Toast.LENGTH_SHORT).show();
                staggeredGridManager();
                break;

            case R.id.fan:
                Toast.makeText(mActivity, R.string.fan, Toast.LENGTH_SHORT).show();
                break;

            case R.id.carousel:
                Toast.makeText(mActivity, R.string.carousel, Toast.LENGTH_SHORT).show();
                break;

            case R.id.chips:
                Toast.makeText(mActivity, R.string.chips, Toast.LENGTH_SHORT).show();
                break;


            case R.id.hive:
                Toast.makeText(mActivity, R.string.hive, Toast.LENGTH_SHORT).show();
                break;

            case R.id.vLayout:
                Toast.makeText(mActivity, R.string.vLayout, Toast.LENGTH_SHORT).show();
                break;

            case R.id.flexBox:
                Toast.makeText(mActivity, R.string.flexBox, Toast.LENGTH_SHORT).show();
                break;

            case R.id.londonEye:
                Toast.makeText(mActivity, R.string.londonEye, Toast.LENGTH_SHORT).show();
                break;

            case R.id.ZLayout:
                Toast.makeText(mActivity, R.string.ZLayout, Toast.LENGTH_SHORT).show();
                break;

            case R.id.gallery:
                Toast.makeText(mActivity, R.string.gallery, Toast.LENGTH_SHORT).show();
                break;

            case R.id.custom:
                Toast.makeText(mActivity, R.string.custom, Toast.LENGTH_SHORT).show();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void staggeredGridManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void linearManager() {
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void gridManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 10 == 0) {
                    Log.d(TAG, "position= " + position);
                    return 2;
                }
                return 1;


            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.tag_layout, parent, false);
            return new MyAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tag_tv);
            }
        }
    }


}
