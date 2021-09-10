package com.xxm.review.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.utils.DensityUtils;

/**
 * 自定义点赞View
 */
public class LikeView extends View {


    //设置wrap_content时候View的大小
    private int defaultWidth;
    private int defaultHeight;
    private Drawable unselectedDrawable;
    private Rect unselectedRect = new Rect();
    private int centerX;
    private int centerY;


    public LikeView(Context context) {
        super(context);
        init(context);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    /**
     * 初始化
     */
    private void init(Context context) {
        defaultWidth = DensityUtils.dp2px(context, 32);
        defaultHeight = DensityUtils.dp2px(context, 32);

        unselectedDrawable = getResources().getDrawable(R.drawable.ic_messages_like_unselected);
    }


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

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.RED);
        unselectedRect.left = centerX - unselectedDrawable.getIntrinsicWidth() / 2;
        unselectedRect.top = centerY - unselectedDrawable.getIntrinsicHeight() / 2;
        unselectedRect.right = centerX + unselectedDrawable.getIntrinsicWidth() / 2;
        unselectedRect.bottom = centerY + unselectedDrawable.getIntrinsicHeight() / 2;

        unselectedDrawable.setBounds(unselectedRect);
        unselectedDrawable.draw(canvas);

    }


}
