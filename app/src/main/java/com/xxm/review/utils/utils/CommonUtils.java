package com.xxm.review.utils.utils;


import android.content.Context;
import android.graphics.PixelFormat;
import android.view.WindowManager;
import android.widget.TextView;

public class CommonUtils {


    /**
     * 把View对象挂载到窗体上面
     *
     * @param address 内容
     * @param context 上下文
     */
    public void showLoaction(String address, Context context) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        TextView view = new TextView(context);
        view.setText(address);
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        windowManager.addView(view, params);
    }

}
