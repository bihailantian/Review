package com.xxm.review.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.xxm.review.R;

/**
 * 分段进度条
 */
public class SegmentedProgressView extends View {

    private static final String TAG = SegmentedProgressView.class.getSimpleName();

    private static final int DEFAULT_TOTAL = 30;
    private static final int DEFAULT_SELECT_COLOR = Color.parseColor("#008000");
    //矩形个数
    private int mTotal = DEFAULT_TOTAL;
    //背景颜色
    private int mBgColor = Color.GRAY;
    //选中颜色
    private int mSelectColor = DEFAULT_SELECT_COLOR;
    //矩形宽度
    private int mRectWidth = 20;
    //矩形高度
    private int mRectHeight = 30;
    //间隔
    private int space = 5;
    //进度值
    private int mPrecess = 0;
    //矩形顶部
    private int mTop = 10;
    //矩形底部
    private int mBottom = mTop + mRectHeight;
    //左边距
    private int mPaddingX = 10;
    //画笔
    private Paint mPaint;
    //未选中矩形片段的边框宽度
    private float noSelectRectStrokeWidth = 2;

    public SegmentedProgressView(Context context) {
        super(context);
        init(null);
    }

    public SegmentedProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SegmentedProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        if (attrs != null) {
            initAttrs(attrs);
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedProgressView);
        mTotal = typedArray.getInt(R.styleable.SegmentedProgressView_segmentedTotal, DEFAULT_TOTAL);
        mBgColor = typedArray.getColor(R.styleable.SegmentedProgressView_segmentedBgColor, Color.GRAY);
        mSelectColor = typedArray.getColor(R.styleable.SegmentedProgressView_segmentedProgressColor, DEFAULT_SELECT_COLOR);
        space = typedArray.getDimensionPixelSize(R.styleable.SegmentedProgressView_segmentedProgressSpace, 5);
        mPrecess = typedArray.getInt(R.styleable.SegmentedProgressView_segmentedProcess, 0);

        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        //Log.d(TAG, "measuredHeight=" + measuredHeight + "  ,measuredWidth=" + measuredWidth);
        //mTotal = measuredWidth / (mRectWidth + space);
        mRectWidth = (measuredWidth - mPaddingX) / mTotal - space;
        mRectHeight = measuredHeight - mTop * 2;
        mBottom = measuredHeight - mTop;

        //Log.d(TAG, "measuredWidth:" + measuredWidth);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //Log.d(TAG, "onLayout ");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 1; i <= mTotal; i++) {
            drawRect(i, canvas);
        }
        //Log.d(TAG, "======================================================================");
    }

    /**
     * @param i
     * @param canvas
     */
    private void drawRect(int i, Canvas canvas) {
        if (i <= mPrecess) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mSelectColor);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(noSelectRectStrokeWidth);
            //mPaint.setColor(Color.RED);
            mPaint.setColor(mBgColor);
        }
        int startX = getLeft(i);
        canvas.drawRect(startX, mTop, startX + mRectWidth, mBottom, mPaint);
        //Log.d(TAG, "i=" + i + ", left:" + startX + ", right:" + (startX + mRectWidth) + ", mRectWidth=" + mRectWidth);
    }

    private int getLeft(int i) {
        return (i - 1) * (mRectWidth + space) + mPaddingX;
    }

    /**
     * 设置进度
     *
     * @param process 进度
     */
    public void setProcess(int process) {
        this.mPrecess = process;
        invalidate();
    }

    /**
     * 设置总进度
     *
     * @param total 总进度
     */
    public void setTotal(int total) {
        this.mTotal = total;
        invalidate();
    }


    /**
     * 设置未选中矩形片段的边框宽度
     *
     * @param noSelectRectStrokeWidth 边框宽度
     */
    public void setNoSelectRectStrokeWidth(float noSelectRectStrokeWidth) {
        this.noSelectRectStrokeWidth = noSelectRectStrokeWidth;
        invalidate();
    }

}
