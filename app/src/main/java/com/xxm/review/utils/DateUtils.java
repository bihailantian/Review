package com.xxm.review.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    private static final String TGA = DateUtils.class.getSimpleName();

    /**
     * 字符串日期格式的计算两个日期之间的天数,minDate > maxDate 返回-1
     *
     * @param minDate  较小的时间
     * @param maxDate  较大的时间
     * @param inFormat 日期格式
     */
    public static int daysBetween(String minDate, String maxDate, String inFormat) {
        int result = -1;

        SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(minDate));
            long minTime = cal.getTimeInMillis();
            cal.setTime(sdf.parse(maxDate));
            long maxTime = cal.getTimeInMillis();
            long between_days = (maxTime - minTime) / (1000 * 3600 * 24);
            if (between_days >= 0) {
                result = Integer.parseInt(String.valueOf(between_days));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 将源字符串sformate时间格式转化成目标rformate时间格式
     *
     * @param date
     * @param sFormate
     * @param rFormate
     * @return
     */
    public static String getFormatDate(String date, String sFormate, String rFormate) {
        String dateResultStr = "";
        if (!TextUtils.isEmpty(date)) {
            try {
                Date dateD = new SimpleDateFormat(sFormate).parse(date);
                dateResultStr = new SimpleDateFormat(rFormate).format(dateD);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateResultStr;
    }


    /**
     * 开始日期
     *
     * @return
     */
    public static String getBeginDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        String beginDate = sdf.format(calendar.getTime());
        return beginDate;
    }

    /**
     * 结束日期
     *
     * @return
     */
    public static String getEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
        String endDate = sdf.format(calendar.getTime());
        return endDate;
    }

    //显示日历
    public static void showCalendar(Context context, final TextView textView, final String inFormat) {

        final Calendar calendar = Calendar.getInstance();


        final DatePickerDialog mDialog = new DatePickerDialog(context, null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int monthOfYear = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                calendar.set(year, monthOfYear, dayOfMonth);
                textView.setText(DateFormat.format(inFormat, calendar));

            }
        });
        //取消按钮
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TGA, "取消");
            }
        });

        mDialog.show();
    }


    //显示日历
    public static void showCalendar(Context context, final TextView textView, @NonNull Date currentDate, final String inFormat) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);


        final DatePickerDialog mDialog = new DatePickerDialog(context, null, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int monthOfYear = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                calendar.set(year, monthOfYear, dayOfMonth);
                textView.setText(DateFormat.format(inFormat, calendar));

            }
        });
        //取消按钮
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TGA, "取消");
            }
        });

        mDialog.show();
    }


}
