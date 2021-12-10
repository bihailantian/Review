package com.xxm.review.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R
import com.xxm.review.utils.BitmapUtil

class ImageInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_info)

        val tvImageInfo: TextView = findViewById(R.id.tv_imageInfo)

        val imagePath = "/mnt/sdcard/DCIM/Camera/21041009_20211126231238.jpg"
        val imagePath2 = "/mnt/sdcard/DCIM/Camera/IMG_20211207_063203_1.jpg"

        tvImageInfo.text = "旋转角度:" + BitmapUtil.getBitmapDegree(imagePath)


    }
}