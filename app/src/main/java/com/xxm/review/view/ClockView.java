package com.xxm.review.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.utils.DensityUtils;

import java.util.Calendar;

public class ClockView extends View {

    private static final String TAG = ClockView.class.getSimpleName();
    //画笔
    private Paint mPaint;
    //半径
    private int mRadius;
    private int centerX;
    private int centerY;

    private int maxScale;
    private int minScale;
    private Rect textBounds = new Rect();
    private int textSize;
    // 刻度与数字间距
    private float mNumberSpace = 10f;

    //设置wrap_content时候View的大小
    private int defaultWidth = 100;
    private int defaultHeight = 100;

    private float hourNeedleWidth = 15f;
    private float minuteWidth = 10f;
    private float secondWidth = 4f;

    private int hourNeedleWidthColor;
    private int minuteWidthColor;
    private int secondWidthColor;

    public ClockView(Context context) {
        this(context, null);

    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        Log.d(TAG, "ClockView");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.ClockView_clockRadius, DensityUtils.dp2px(context, 50));
        hourNeedleWidthColor = typedArray.getColor(R.styleable.ClockView_hourNeedleWidthColor, Color.BLUE);
        minuteWidthColor = typedArray.getColor(R.styleable.ClockView_minuteWidthColor, Color.BLACK);
        secondWidthColor = typedArray.getColor(R.styleable.ClockView_secondWidthColor, Color.RED);
        textSize = typedArray.getDimensionPixelSize(R.styleable.ClockView_textSize,DensityUtils.sp2px(context, 8));
        typedArray.recycle();
    }



    private void init(Context context) {
        minScale = DensityUtils.dp2px(context, 3);
        maxScale = minScale * 2;

        defaultWidth = DensityUtils.dp2px(context, 110);
        defaultHeight = DensityUtils.dp2px(context, 110);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

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

        Log.d(TAG, "onMeasure");
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;


    }

    private void checkVal(int width, int height) {
        Log.d(TAG, "width:" + width + "  height:" + height + "  mRadius:" + mRadius);
        if (mRadius > Math.min(width, height) / 2) {
            mRadius = Math.min(width, height) / 2;
            Log.d(TAG, "mRadius:" + mRadius);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //校验
        checkVal(getWidth(), getHeight());
        //canvas.drawColor(Color.RED);
        canvas.translate(centerX, centerY);

        drawCircle(canvas);
        //绘制刻度线和文字
        drawScaleAndText(canvas);
        //绘制指针
        drawClockNeedle(canvas);


        postInvalidateDelayed(1000);

    }


    /**
     * 绘制指针
     *
     * @param canvas 画布
     */
    private void drawClockNeedle(Canvas canvas) {
        Calendar instance = Calendar.getInstance();
        int hour = instance.get(Calendar.HOUR);
        int minute = instance.get(Calendar.MINUTE);
        int second = instance.get(Calendar.SECOND);

        Log.e(TAG, hour + ":" + minute + ":" + second);

        //计算旋转角度
        float hourAngle = (hour + minute / 60f) * 360 / 12;
        float minuteAngle = (minute + second / 60f) * 360 / 60f;
        float secondAnge = second * 360 / 60f;


        //绘制时针
        canvas.save();
        canvas.rotate(hourAngle, 0, 0);
        mPaint.setColor(hourNeedleWidthColor);
        mPaint.setStrokeWidth(hourNeedleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(-hourNeedleWidth / 2, -mRadius / 2f, hourNeedleWidth / 2, mRadius / 6f, mPaint);
        canvas.restore();


        //绘制分针
        canvas.save();
        canvas.rotate(minuteAngle, 0, 0);
        mPaint.setColor(minuteWidthColor);
        mPaint.setStrokeWidth(minuteWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(-minuteWidth / 2, -mRadius * 0.65f, minuteWidth / 2, mRadius / 6f, mPaint);
        canvas.restore();

        //绘制秒针
        canvas.save();
        canvas.rotate(secondAnge, 0, 0);
        mPaint.setColor(secondWidthColor);
        mPaint.setStrokeWidth(secondWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(-secondWidth / 2, -mRadius + 10, secondWidth / 2, mRadius / 6f, mPaint);
        canvas.restore();

        //绘制原点
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, secondWidth * 3, mPaint);

    }

    /**
     * 绘制刻度线和文字
     *
     * @param canvas 画布
     */
    private void drawScaleAndText(Canvas canvas) {
        for (int i = 1; i <= 60; i++) {
            canvas.rotate(6f, 0, 0);

            if (i % 5 == 0) {
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(3f);
                canvas.drawLine(0, -mRadius, 0, -mRadius + maxScale, mPaint);

                canvas.save();
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(textSize);
                mPaint.setStrokeWidth(1);
                String text = String.valueOf(i / 5);
                mPaint.getTextBounds(text, 0, text.length(), textBounds);

                canvas.translate(0f, -mRadius + mNumberSpace + maxScale + (textBounds.height() / 2f));
                canvas.rotate(-6f * i);  //把画布摆正
                canvas.drawText(text, -textBounds.width() / 2f, textBounds.height() / 2f, mPaint);
                canvas.restore();

            } else {
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(1);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawLine(0, -mRadius, 0, -mRadius + minScale, mPaint);
            }

        }
    }

    /**
     * 绘制圆
     *
     * @param canvas 画布
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4f);
        canvas.drawCircle(0, 0, mRadius, mPaint);
        Log.d(TAG,"centerX="+centerX+ " centerY="+centerY);
    }



    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getHourNeedleWidthColor() {
        return hourNeedleWidthColor;
    }

    public void setHourNeedleWidthColor(int hourNeedleWidthColor) {
        this.hourNeedleWidthColor = hourNeedleWidthColor;
    }

    public int getMinuteWidthColor() {
        return minuteWidthColor;
    }

    public void setMinuteWidthColor(int minuteWidthColor) {
        this.minuteWidthColor = minuteWidthColor;
    }

    public int getSecondWidthColor() {
        return secondWidthColor;
    }

    public void setSecondWidthColor(int secondWidthColor) {
        this.secondWidthColor = secondWidthColor;
    }

}
