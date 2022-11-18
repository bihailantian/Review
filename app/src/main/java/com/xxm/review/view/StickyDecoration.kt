package com.xxm.review.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xxm.review.utils.DensityUtils
import kotlin.math.max

/**
 * 实现的悬浮效果
 * 参考：https://github.com/yoonerloop/StickyRecycleView
 * 参考：https://github.com/Gavin-ZYX/StickyDecoration
 */
class StickyDecoration(val context: Context, private val callback: DecorationCallback) : RecyclerView.ItemDecoration() {
    private val TAG = "StickyDecoration"
    private val paint = Paint()
    private val textPaint = TextPaint()
    private var topHead = 0
    private var paddingLeft = 0

    init {

        paint.color = Color.DKGRAY
        textPaint.typeface = Typeface.DEFAULT_BOLD
        textPaint.isFakeBoldText = false
        textPaint.isAntiAlias = true
        textPaint.textSize = 40f
        textPaint.color = Color.WHITE
        textPaint.textAlign = Paint.Align.LEFT
        topHead = DensityUtils.dp2px(context, 32f)
        paddingLeft = DensityUtils.dp2px(context, 8f)
    }


    /**
     * 设置ItemDecoration背景颜色
     * @param color Int 颜色值
     */
    fun setDecorationBackgroundColor(color: Int) {
        paint.color = color
    }

    /**
     * 设置文本字体大小
     * @param textSize Float
     */
    fun setTextSize(textSize: Float) {
        textPaint.textSize = textSize
    }

    /**
     * 设置文本颜色
     * @param color Int
     */
    fun setTextColor(color: Int) {
        textPaint.color = color
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        val data = callback.getData(pos)
        if (data.isNullOrEmpty()) {
            return
        }

        //同组的第一个才添加padding
        if (pos == 0 || isHeader(pos)) {
            outRect.top = topHead
        } else {
            outRect.top = 0
        }
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        Log.i(TAG, "onDrawOver")
        //获取当前可见的item的数量，不包括分组项，注意区分下面的
        val itemCount = state.itemCount
        //获取所有的的item个数,不建议使用Adapter中获取
        val childCount = parent.childCount
        val left = parent.left + parent.paddingLeft
        val right = parent.right - parent.paddingRight


        var preData: String?
        var currentData: String? = ""
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            val textLine = callback.getData(position)
            preData = currentData
            currentData = textLine
            if (textLine.isNullOrEmpty() || currentData.isNullOrEmpty() || currentData.equals(preData, false)) {
                continue
            }

            val viewBottom = view.bottom
            var textY = max(topHead, view.top)
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                val nextData = callback.getData(position + 1)
                if (currentData != nextData && viewBottom < textY) { //组内最后一个view进入了header
                    textY = viewBottom
                }
            }

            val rect = Rect(left, textY - topHead, right, textY)
            c.drawRect(rect, paint)

            //绘制文字基线，文字的的绘制是从绘制的矩形底部开始的
            val fontMetrics = textPaint.fontMetrics
            val baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2
            //textPaint.textAlign = Paint.Align.CENTER //文字居中

            Log.i(TAG, "onDrawOver textLine=$textLine")
            //绘制文本
            c.drawText(textLine, (rect.left + paddingLeft).toFloat(), baseline, textPaint)
        }


    }

    private fun isHeader(pos: Int): Boolean {
        return if (pos == 0) {
            true
        } else {
            val preData = callback.getData(pos - 1)
            val data = callback.getData(pos)
            preData != data
        }
    }

    interface DecorationCallback {
        fun getData(position: Int): String?
    }
}