package com.example.kotlin_coroutines.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author benzly
 * @date 2020-03-19
 * <p>
 * 一个显示调试信息的View，只在调试时会被调起
 */
public class DevInfoView extends RecyclerView {

    private static class TextAttach {
        public String text;
        public int color;
    }

    private final List<TextAttach> devInfo = new ArrayList<>();
    private final MyAdapter adapter = new MyAdapter();
    private SimpleDateFormat format = new SimpleDateFormat(" HH:mm:ss.SSS");
    private String lastInfo;
    private int ignoreCount;

    public DevInfoView(@NonNull Context context) {
        this(context, null);
    }

    public DevInfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DevInfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setVerticalScrollBarEnabled(true);
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(adapter);
        addDevInfo("=================begin=================");
    }

    public void addWarnInfo(String text) {
        addDevInfo(text, Color.parseColor("#FFA500"));
    }

    public void addErrorInfo(String text) {
        addDevInfo(text, Color.RED);
    }

    public void addDevInfo(String text) {
        addDevInfo(text, Color.BLACK);
    }

    private void addDevInfo(String text, int color) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            final String temp = text;
            post(() -> addDevInfo(temp, color));
            return;
        }
        Log.d("addDevInfo", text);
        if (!TextUtils.isEmpty(lastInfo) && lastInfo.equals(text)) {
            //与上一条消息相同，此处进行缩减处理
            ignoreCount++;
            text = format.format(new Date()) + ":  与上一条信息相同, 重复:" + ignoreCount;
        } else {
            ignoreCount = 0;
            lastInfo = text;
        }

        TextAttach attach = new TextAttach();
        attach.text = text;
        attach.color = color;

        if (ignoreCount >= 3) {
            devInfo.set(devInfo.size() - 1, attach);
            adapter.notifyItemChanged(devInfo.size() - 1);
            return;
        }

        boolean isBottom = !canScrollVertically(1);
        attach.text = format.format(new Date()) + ":  " + text + "";
        devInfo.add(attach);
        adapter.notifyItemInserted(devInfo.size());
        if (isBottom) {
            (getLayoutManager()).scrollToPosition(devInfo.size() - 1);
        }
    }

    class MyViewHolder extends ViewHolder {

        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }

    class MyAdapter extends Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(10);
            textView.setTextColor(Color.BLACK);
            return new MyViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv.setText(devInfo.get(position).text);
            holder.tv.setTextColor(devInfo.get(position).color);
        }

        @Override
        public int getItemCount() {
            return devInfo.size();
        }
    }
}
