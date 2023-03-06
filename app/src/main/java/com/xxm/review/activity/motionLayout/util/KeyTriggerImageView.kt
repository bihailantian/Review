package com.xxm.review.activity.motionLayout.util

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.xxm.review.R


/**
 *
 * @ClassName: KeyTriggerImageView
 */
class KeyTriggerImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
        AppCompatImageView(context, attrs, defStyleAttr) {

    // 显示 view
    fun show() {
        visibility = View.VISIBLE
    }

    // 隐藏当前 view
    fun hide() {
        visibility = View.GONE
    }

    // 替换图片 1
    fun changeImage1() {
        setImageBitmap(BitmapFactory.decodeResource(resources,
                R.mipmap.guidao_a))
    }

    // 替换图片 2
    fun changeImage2() {
        setImageBitmap(BitmapFactory.decodeResource(resources,
                R.mipmap.guidao_b))
    }

    // 原始照片
    fun originalImage() {
        setImageBitmap(BitmapFactory.decodeResource(resources,
                R.mipmap.no_data))
    }

}