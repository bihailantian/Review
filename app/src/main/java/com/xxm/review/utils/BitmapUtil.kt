package com.xxm.review.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import java.io.IOException

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


    /**
     * 获取图片的旋转角度
     * <p>https://www.jianshu.com/p/f269bcda335f#
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    fun getBitmapDegree(path: String): Int {
        var degree = 0
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            val exifInterface = ExifInterface(path)
            // 获取图片的旋转信息
            val orientation: Int = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    fun rotateBitmapByDegree(bitmap: Bitmap, degree: Int): Bitmap {
        // 根据旋转角度，生成旋转矩阵
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        if (bitmap != null && !bitmap.isRecycled) {
            bitmap.recycle()
        }
        return newBitmap
    }

}