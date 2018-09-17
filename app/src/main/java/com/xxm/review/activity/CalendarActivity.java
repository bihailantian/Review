package com.xxm.review.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Calendar 日期选择
 */
public class CalendarActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CalendarActivity.class.getSimpleName();
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        tvDate = findViewById(R.id.tv_date);
        tvDate.setOnClickListener(this);


        CalendarView mCalendar = findViewById(R.id.calender);
        mCalendar.setMaxDate(new Date().getTime());
//        mCalendar.setMaxDate(System.currentTimeMillis());


    }

    @Override
    public void onClick(View v) {
        try {
            String date = tvDate.getText().toString().trim();
            Date currentDate = null;
            if (TextUtils.isEmpty(date)) {
                currentDate = new Date();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                currentDate = sdf.parse(date);
            }
            Log.d(TAG, currentDate.toString());

            DateUtils.showCalendar(CalendarActivity.this, tvDate, currentDate, "yyyy年MM月dd日");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
