package com.xxm.review.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    RecyclerView recyclerView;
    private List<String> list;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mActivity = RecyclerViewActivity.this;
        recyclerView = findViewById(R.id.recyclerView);


        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item " + i);
        }


//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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

        recyclerView.setLayoutManager(gridLayoutManager);

        //添加分割线
        // recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyAdapter());


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
                break;

            case R.id.linear:
                Toast.makeText(mActivity, R.string.linear, Toast.LENGTH_SHORT).show();
                break;

            case R.id.staggeredGrid:
                Toast.makeText(mActivity, R.string.staggeredGrid, Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.tag_layout, parent, false);
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
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
