package com.xxm.review.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ListActivity.class.getSimpleName();
    private int count;
    private ListAdapter adapter;
    private List<String> list;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tvTitle = findViewById(R.id.tv_title);
        Button btnChangeData = findViewById(R.id.btn_change_data);

        ListView listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        count = 1;
        adapter = new ListAdapter(count, list);
        listView.setAdapter(adapter);

        TextView headView = new TextView(this);
        headView.setText("我是headView");
        headView.setTextColor(getResources().getColor(R.color.colorAccent));
        headView.setTextSize(22);
        headView.setGravity(Gravity.CENTER);
        listView.addHeaderView(headView);


        btnChangeData.setOnClickListener(this);
        tvTitle.setText("count=" + count);
    }

    public int getCount() {
        return count;
    }

    @Override
    public void onClick(View v) {
        List<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");
        list2.add("c");
        list = list2;
        count = 3;

        if (adapter != null) {
            adapter.setCount(getCount());
            adapter.notifyDataSetChanged();
        }
        tvTitle.setText("count=" + count);
    }


    class ListAdapter extends BaseAdapter {

        private int count;
        private List<String> list;

        public ListAdapter(int count, List<String> list) {
            this.count = count;
            this.list = list;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public int getCount() {
            return list != null ? list.size() + 1 : 1;
        }

        @Override
        public String getItem(int position) {
            if (position == 0) {
                return "count=" + count;
            }
            return list.get(position - 1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.list_simple_item1, null);
                holder.textView = convertView.findViewById(android.R.id.text1);
                holder.textView2 = convertView.findViewById(android.R.id.text2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(getItem(position));
            holder.textView2.setText(getItem(position));
            holder.textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textClick(position);
                }
            });
            return convertView;
        }

        private void textClick(int position) {
            Log.d(TAG, "position==" + position);
            Toast.makeText(ListActivity.this, "position==" + position, Toast.LENGTH_LONG).show();
        }

        class ViewHolder {
            TextView textView;
            TextView textView2;
        }
    }
}
