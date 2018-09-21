package com.xxm.review.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
