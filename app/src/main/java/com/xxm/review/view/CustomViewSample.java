package com.xxm.review.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewSample extends View {


    public CustomViewSample(Context context) {
        super(context);
    }

    public CustomViewSample(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewSample(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置wrap_content时候View的大小
    private int defaultWidth = 100;
    private int defaultHeight = 100;

    //简单的重写onMeasure
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, defaultHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defaultHeight);
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }
}
