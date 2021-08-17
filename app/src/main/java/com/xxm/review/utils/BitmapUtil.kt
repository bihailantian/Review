package com.xxm.review.utils

import android.graphics.Bitmap
import android.graphics.Matrix

object BitmapUtil {

    /**
     * 水平镜像翻转
     * @param bmp Bitmap
     * @return Bitmap
     */
    fun toHorizontalMirror(bmp: Bitmap): Bitmap {
        val w = bmp.width
        val h = bmp.height
        val matrix = Matrix()
        matrix.postScale(-1f, 1f) // 水平镜像翻转
        return Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true)
    }

    /**
     * 垂直镜像翻转
     * @param bmp Bitmap
     * @return Bitmap
     */
    fun toVerticalMirror(bmp: Bitmap): Bitmap {
        val w = bmp.width
        val h = bmp.height
        val matrix = Matrix()
        matrix.postScale(1f, -1f) // 垂直镜像翻转
        return Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true)
    }
}