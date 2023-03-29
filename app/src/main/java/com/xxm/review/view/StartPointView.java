package com.xxm.review.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

/**
 * 动态线背景
 */
public class StartPointView extends View {

    private static final String TAG = "StartPointView";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int measuredWidth;
    private int measuredHeight;
    private float radius = 8;
    private int pointNum = 50;
    private int maxDistance = 500;
    private Handler handler;
    private HandlerThread mWorkThread;


    public StartPointView(Context context) {
        this(context, null);
    }

    public StartPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "==StartPointView==");
        mPaint.setStrokeWidth(3);

        mWorkThread = new HandlerThread("timing", 16);
        mWorkThread.start();
        handler = new Handler(mWorkThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                invalidate();
            }
        };
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
        Log.d(TAG, "measuredWidth=" + measuredWidth + ", measuredHeight=" + measuredHeight);
    }


    private ArrayList<Point> points = new ArrayList<>();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        mPaint.setColor(Color.WHITE);
        //canvas.drawCircle(100, 100, radius, mPaint);
        //canvas.drawCircle(300, 200, radius, mPaint);

        //canvas.drawLine(100, 100, 300, 200, mPaint);
        points.clear();
        for (int i = 0; i < pointNum; i++) {
            Point point = new Point(radius, getRandom(measuredWidth - radius), getRandom(measuredHeight - radius));
            points.add(point);
            canvas.drawCircle(point.x, point.y, point.radius, mPaint);

        }

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            for (int y = i + 1; y < points.size(); y++) {
                Point point2 = points.get(y);

                //计算两点之间的距离
                //float dx = Math.abs(point.x - point2.x) ;
                //float dy = Math.abs(point.y - point2.y) ;
                double d = Math.sqrt(Math.abs(point.x - point2.x) * Math.abs(point.x - point2.x) + Math.abs(point.y - point2.y) * Math.abs(point.y - point2.y));
                Log.d(TAG, "d=" + d);
                if (d < maxDistance) {
                    int alpha = (int) (255 * (1 - d / maxDistance));
                    Log.d(TAG, "alpha=" + alpha);
                    mPaint.setColor(Color.argb(alpha, 255, 255, 255));
                    canvas.drawLine(point.x, point.y, point2.x, point2.y, mPaint);
                }
            }
        }

        if (handler != null) {
            handler.sendEmptyMessageDelayed(1, 800);
        }


    }

    private Random random = new Random();

    private float getRandom(float bound) {
        return random.nextInt((int) bound);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        if (mWorkThread != null) {
            mWorkThread.getLooper().quitSafely();
            mWorkThread.interrupt();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        mWorkThread = null;
        handler = null;

    }

    private class Point {
        public float radius = 10;
        public float x;
        public float y;

        public Point(float radius, float x, float y) {
            this.radius = radius;
            this.x = x;
            this.y = y;
        }
    }
}
