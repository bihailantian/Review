package com.xxm.review.utils;

import android.view.View;

/**
 * 与View相关的工具类
 */
public class ViewUtil {

    /**
     * 测量一个View没有被渲染（即没有被添加到视图层次中），那么它的宽高通常为0。但是，在某些情况下，你可能需要测量一个未渲染View的宽高。这时可以使用measure()方法进行测量。
     * measure()方法是View类的一个方法，用于测量View的大小。调用该方法后，View会根据测量模式（MeasureSpec）计算出自己的宽高，并保存在getMeasuredWidth()和getMeasuredHeight()方法中。但是，调用measure()方法并不会改变View的布局参数或实际大小，它只是进行一次测量操作。
     * <p>
     * 以下是一个示例代码，展示了如何测量一个未渲染View的宽高：
     *
     * <pre> {@code
     * // 创建一个未渲染的TextView
     * TextView textView = new TextView(context);
     * textView.setText("Hello, world!");
     *
     * // 测量TextView的大小
     * int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
     * int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
     * textView.measure(widthMeasureSpec, heightMeasureSpec);
     *
     * // 获取测量后的宽高
     * int width = textView.getMeasuredWidth();
     * int height = textView.getMeasuredHeight();
     * }</pre>
     *
     * @return
     */
    public int[] measureNotRenderedView(View view) {
        // 测量TextView的大小
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);

        // 获取测量后的宽高
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        int[] viewInfo = new int[2];
        viewInfo[0] = width;
        viewInfo[1] = height;
        return viewInfo;
    }
}
