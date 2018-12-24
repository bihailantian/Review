package com.xxm.review.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 带动画效果的按钮
 */

public class AnimationButton extends View {


    private static final String TAG = AnimationButton.class.getSimpleName();
    /**
     * 圆角矩形画笔
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;

    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;

    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;

    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectf = new RectF();

    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;

    /**
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;

    /**
     * 圆角半径
     */
    private int circleAngle;


    public AnimationButton(Context context) {
        this(context, null);
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(bg_color);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged --> w=" + w + ",  h=" + h + ",  oldw=" + oldw + ",  oldh=" + oldh);

        width = w;
        height = h;

        default_two_circle_distance = (w - h) / 2;


        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw");
        super.onDraw(canvas);
    }

    /**
     * 绘制长方形变成圆形
     *
     * @param canvas 画布
     */
    private void draw_oval_to_circle(Canvas canvas) {

        rectf.left = two_circle_distance;
        rectf.top = 0;
        rectf.right = width - two_circle_distance;
        rectf.bottom = height;

        //画圆角矩形
        canvas.drawRoundRect(rectf, circleAngle, circleAngle, paint);

    }
}
