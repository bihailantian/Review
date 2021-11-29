package com.szgentech.activityresultsdemo; /**
 * ViewDragHelper  2017-11-20
 * Copyright (c) 2017 KL Co.Ltd. All right reserved.
 */

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.customview.widget.ViewDragHelper;

/**
 * class description here
 *
 * @author Jackson
 * @version 1.0.0
 *          since 2017 11 20
 */
public class ViewDrag extends LinearLayout {

    private ViewDragHelper mViewDragHelper;

    private Point mPoint = new Point();

    public ViewDrag(Context context) {
        super(context);
        init();
    }

    public ViewDrag(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewDrag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);  //ViewDragHelper实例化
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == dragView1 || child == dragView2;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int leftBound = getPaddingLeft();
            int rightBound = getWidth() - child.getWidth() - leftBound;
            //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
            //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
            //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
            return Math.min(Math.max(left, leftBound), rightBound);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - child.getHeight() - topBound;
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == dragView2) {
                mViewDragHelper.settleCapturedViewAt(mPoint.x, mPoint.y);
                invalidate();
            }
        }
    };

    @Override
    public void computeScroll() {
       if (mViewDragHelper.continueSettling(true)){
           invalidate();
       }
    }

    View dragView1;
    View dragView2;
    View dragView3;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       /* dragView1=findViewById(R.id.tv_1);
        dragView2=findViewById(R.id.tv_2);
        dragView3=findViewById(R.id.tv_3);*/
        dragView1 = getChildAt(0);
        dragView2 = getChildAt(1);
        dragView3 = getChildAt(2);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //布局完成的时候就记录一下位置
        mPoint.x = dragView2.getLeft();
        mPoint.y = dragView2.getTop();
    }
}

