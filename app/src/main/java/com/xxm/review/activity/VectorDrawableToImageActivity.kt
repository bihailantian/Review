package com.xxm.review.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.xxm.review.R
import com.xxm.review.utils.DensityUtils
import com.xxm.review.utils.ImageUtils

/**
 * Android Vector转成png、jpg图片
 */
class VectorDrawableToImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vector_drawable_to_image)

        val icBaselineCheck24 = findViewById<ImageView>(R.id.ic_baseline_check_24)
        val icNoPass = findViewById<ImageView>(R.id.ic_no_pass)
        val icNotHappen = findViewById<ImageView>(R.id.ic_not_happen)

        findViewById<View>(R.id.btn_vector2Img).setOnClickListener {
            Thread {
                Log.i("VectorDrawableToImage-", "resToPng start")
                resToPng(drawableToBitmap(icBaselineCheck24), "ic_baseline_check_24.png")
                resToPng(drawableToBitmap(icNoPass), "ic_no_pass.png")
                resToPng(drawableToBitmap(icNotHappen), "ic_not_happen.png")
                Log.i("VectorDrawableToImage-", "resToPng end")
            }.start()
        }


    }

    private fun drawableToBitmap(imageView: ImageView): Bitmap {
        val dp2px = DensityUtils.dp2px(this@VectorDrawableToImageActivity, 24f)
        return imageView.drawable.toBitmap(dp2px, dp2px)
    }

    private fun resToPng(bitmap: Bitmap, name: String) {

        ImageUtils.saveBitmap(this@VectorDrawableToImageActivity, bitmap, name, getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path) // /storage/emulated/0/Android/data/com.xxm.review/files/Download
    }

}