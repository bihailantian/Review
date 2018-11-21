package com.xxm.review.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

public class GridViewActivity extends BaseActivity {

    private String[] provinceNames = new String[]{"北京", "上海", "广东", "广西", "天津", "重庆", "湖北", "湖南", "河北", "河南", "山东"};
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        gridView = findViewById(R.id.gv);
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
    }


    private class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return provinceNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_grid, null);
                holder = new ViewHolder();
                holder.ivGrid = convertView.findViewById(R.id.iv_grid);
                holder.tvGrid = convertView.findViewById(R.id.tv_grid);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvGrid.setText(provinceNames[position]);
            return convertView;
        }


        class ViewHolder {
            TextView tvGrid;
            ImageView ivGrid;
        }
    }


}
