package com.xxm.review.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {


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
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

/*
        TextView textView = new TextView(this);
        textView.setText("textView");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(15);
        textView.setTextColor(Color.RED);
        layoutManager.addView(textView, 0);
*/

        recyclerView.setLayoutManager(layoutManager);
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyAdapter());


    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mActivity).inflate(android.R.layout.simple_list_item_1, null);
            return new ViewHolder(itemView);
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
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }


}
