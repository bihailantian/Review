package com.xxm.review.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 气泡
 */
public class PracticeBubbleView extends View {

    private static final String TAG = PracticeBubbleView.class.getSimpleName();
    // 气泡最大半径 px
    private static final int mBubbleMaxRadius = 30;
    // 气泡最小半径 px
    private static final int mBubbleMinRadius = 5;
    // 气泡最大数量
    private static final int mBubbleMaxSize = 30;
    // 刷新间隔
    private int mBubbleRefreshTime = 20;
    // 气泡上升最大速度
    private static final int mBubbleMaxSpeedY = 5;
    // 气泡画笔
    private int mBubbleAlpha = 128;
    // 瓶子宽度
    private float mBottleWidth;
    // 瓶子高度
    private float mBottleHeight;
    // 瓶子底部转角半径
    private float mBottleRadius;
    // 瓶子边缘宽度
    private float mBottleBorder;
    // 瓶子顶部转角半径
    private float mBottleCapRadius;
    // 水的高度
    private float mWaterHeight;
    // 实际可用内容区域
    private RectF mContentRectF;
    // 水占用的区域
    private RectF mWaterRectF;
    // 外部瓶子
    private Path mBottlePath;
    // 水
    private Path mWaterPath;
    // 瓶子画笔
    private Paint mBottlePaint;
    // 水画笔
    private Paint mWaterPaint;
    // 气泡画笔
    private Paint mBubblePaint;
    //瓶子轮廓的颜色
    private int mBottleContourColor = Color.RED;
    //汽包线程
    private Thread mBubbleThread;
    //汽包集合
    private ArrayList<Bubble> mBubbles = new ArrayList<>();
    //随机数对象
    private Random random = new Random();

    public PracticeBubbleView(Context context) {
        this(context, null);
    }

    public PracticeBubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PracticeBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mWaterRectF = new RectF();

        mBottleWidth = dp2px(130);

        mBottleHeight = dp2px(260);

        mBottleBorder = dp2px(8);

        mBottleRadius = dp2px(15);

        mBottleCapRadius = dp2px(5);

        mWaterHeight = dp2px(220);

        mBottlePath = new Path();

        mWaterPath = new Path();

        mBottlePaint = new Paint();

        mBottlePaint.setAntiAlias(true);

        mBottlePaint.setStyle(Paint.Style.STROKE);

        mBottlePaint.setStrokeCap(Paint.Cap.ROUND);

        mBottlePaint.setColor(Color.WHITE);

        mBottlePaint.setStrokeWidth(mBottleBorder);

        mWaterPaint = new Paint();

        mWaterPaint.setAntiAlias(true);

        initBubble();
    }

    private void initBubble() {
        mBubblePaint = new Paint();

        mBubblePaint.setColor(Color.WHITE);

        mBubblePaint.setAlpha(mBubbleAlpha);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mWaterPath, mWaterPaint);
        mBottlePaint.setColor(mBottleContourColor); //瓶子轮廓的颜色
        canvas.drawPath(mBottlePath, mBottlePaint);
        drawBubble(canvas);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");
        mContentRectF = new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());

        //内容中心点X 减去瓶子宽度的一半
        float bl = mContentRectF.centerX() - mBottleWidth / 2;

        //内容中心点Y 减去瓶子高度的一半
        float bt = mContentRectF.centerY() - mBottleHeight / 2;

        //内容中心点X 加上瓶子宽度的一半
        float br = mContentRectF.centerX() + mBottleWidth / 2;

        //内容中心点Y 减去瓶子高度的一半
        float bb = mContentRectF.centerY() + mBottleHeight / 2;

        //瓶子
        mBottlePath.reset();

        mBottlePath.moveTo(bl - mBottleCapRadius, bt - mBottleCapRadius);

        mBottlePath.quadTo(bl, bt - mBottleCapRadius, bl, bt);

        mBottlePath.lineTo(bl, bb - mBottleRadius);

        mBottlePath.quadTo(bl, bb, bl + mBottleRadius, bb);

        mBottlePath.lineTo(br - mBottleRadius, bb);

        mBottlePath.quadTo(br, bb, br, bb - mBottleRadius);

        mBottlePath.lineTo(br, bt);

        mBottlePath.quadTo(br, bt - mBottleCapRadius, br + mBottleCapRadius, bt - mBottleCapRadius);

        //瓶子里的水
        mWaterPath.reset();

        mWaterPath.moveTo(bl, bb - mWaterHeight);

        mWaterPath.lineTo(bl, bb - mBottleRadius);

        mWaterPath.quadTo(bl, bb, bl + mBottleRadius, bb);

        mWaterPath.lineTo(br - mBottleRadius, bb);

        mWaterPath.quadTo(br, bb, br, bb - mBottleRadius);

        mWaterPath.lineTo(br, bb - mWaterHeight);
//
        mWaterPath.close();

        mWaterRectF.set(bl, bb - mWaterHeight, br, bb);

        LinearGradient gradient = new LinearGradient(mWaterRectF.centerX(), mWaterRectF.top,

                mWaterRectF.centerX(), mWaterRectF.bottom, 0xFF4286f4, 0xFF373B44, Shader.TileMode.CLAMP);

        mWaterPaint.setShader(gradient);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startBubbleSync();
    }

    /**
     * 开启汽包线程
     */
    private void startBubbleSync() {
        stopBubbleSync();
        mBubbleThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(mBubbleRefreshTime);

                        tryCreateBubble();
                        refreshBubbles();
                        postInvalidate();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "创建汽包出错");
                        e.printStackTrace();
                        break;
                    }
                }


            }
        });

        mBubbleThread.start();


    }


    /**
     * 刷新汽包
     */
    private void refreshBubbles() {

        List<Bubble> list = new ArrayList<>(mBubbles);

        for (Bubble bubble : list) {

            if (bubble.y - bubble.speedY <= mWaterRectF.top + bubble.radius) { //汽包上升到顶了

                mBubbles.remove(bubble); //移除bubble对象

            } else {
                //获取对象的下标
                int i = mBubbles.indexOf(bubble);

                if (bubble.x + bubble.speedX <= mWaterRectF.left + bubble.radius + mBottleBorder / 2) { //到左边边缘了

                    bubble.x = mWaterRectF.left + bubble.radius + mBottleBorder / 2;

                } else if (bubble.x + bubble.speedX >= mWaterRectF.right - bubble.radius - mBottleBorder / 2) { //到右边边缘了

                    bubble.x = mWaterRectF.right - bubble.radius - mBottleBorder / 2;

                } else {

                    bubble.x = bubble.x + bubble.speedX;

                }

                bubble.y = bubble.y - bubble.speedY;

                mBubbles.set(i, bubble);

            }
        }
    }

    /**
     * 创建汽包
     */
    private void tryCreateBubble() {
        if (null == mContentRectF || mBubbles.size() >= mBubbleMaxSize || random.nextFloat() < 0.9)
            return;


        Bubble bubble = new Bubble();

        int radius = random.nextInt(mBubbleMaxRadius - mBubbleMinRadius);

        radius += mBubbleMinRadius;

        float speedY = random.nextFloat() * mBubbleMaxSpeedY;

        while (speedY < 1) {
            speedY = random.nextFloat() * mBubbleMaxSpeedY;
        }

        bubble.radius = radius;

        bubble.speedY = speedY;

        bubble.x = mWaterRectF.centerX();

        bubble.y = mWaterRectF.bottom - radius - mBottleBorder / 2;

        float speedX = random.nextFloat() - 0.5f;

        while (speedX == 0) {

            speedX = random.nextFloat() - 0.5f;

        }

        bubble.speedX = speedX * 2;

        bubble.color = getRandColor();


        mBubbles.add(bubble);


    }

    /**
     * 颜色的随机值
     *
     * @return String
     */
    public static int getRandColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

    }


    /**
     * 绘制汽包
     *
     * @param canvas 画布
     */
    private void drawBubble(Canvas canvas) {
        List<Bubble> list = new ArrayList<>(mBubbles);
        Log.e(TAG, "汽包个数:" + list.size());
        for (Bubble bubble : list) {
            if (bubble != null) {
                Log.e(TAG, bubble.toString());
                mBubblePaint.setColor(bubble.color);
                canvas.drawCircle(bubble.x, bubble.y, bubble.radius, mBubblePaint);
            }
        }


    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopBubbleSync();
    }

    /**
     * 停止汽包线程
     */
    private void stopBubbleSync() {
        if (mBubbleThread != null) {
            mBubbleThread.interrupt();
            mBubbleThread = null;
        }
    }

    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }


    /**
     * 瓶子轮廓的颜色
     *
     * @param bottleContourColor 颜色值
     */
    public void setBottleContourColor(int bottleContourColor) {
        this.mBottleContourColor = bottleContourColor;
        postInvalidate();
    }

    class Bubble {
        int color; //汽包颜色
        float radius; //半径
        float speedY; //上升速度
        float speedX; //平移速度
        float x; //汽包x坐标
        float y; //汽包y坐标

        @Override
        public String toString() {
            return "Bubble{" +
                    "color=" + color +
                    ", radius=" + radius +
                    ", speedY=" + speedY +
                    ", speedX=" + speedX +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
