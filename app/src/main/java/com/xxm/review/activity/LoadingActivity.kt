package com.xxm.review.activity


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R
import com.xxm.review.databinding.ActivityLoadingBinding
import com.xxm.review.utils.LoadingUtils


class LoadingActivity : AppCompatActivity() {
    private val TAG = "LoadingActivity-"
    private lateinit var mBinding: ActivityLoadingBinding
    private val dialogUtils by lazy {
        LoadingUtils(this)
    }
    private val dialogUtils2 by lazy {
        LoadingUtils(this)
    }
    private var text: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Log.d(TAG, "onCreate")
        mBinding.showLoading.setOnClickListener {
            showLoading()
            it.handler.postDelayed({
                dismissLoading()
            }, 5000)
        }
        mBinding.showLoading2.setOnClickListener {
            dialogUtils2.showLoading()
            it.handler.postDelayed({
                dialogUtils2.dismissLoading()
            }, 5000)
        }

        mBinding.showPopupWindow.setOnClickListener {
            showPopupWindow()
        }
    }

    private fun showPopupWindow() {
        // 创建PopupWindow布局
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null)


        // 创建PopupWindow
        val popupWindow = PopupWindow(
            contentView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT, true
        )
        // 设置背景
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        // 设置阴影效果
        popupWindow.elevation = 200f

        // 设置外部点击关闭
        popupWindow.isOutsideTouchable = true
        /*// 设置背景阴影效果
        setPopupWindowShadow(popupWindow)*/


        // 设置PopupWindow的位置
        popupWindow.showAtLocation(
            mBinding.showPopupWindow,
            Gravity.CENTER,
            0,
            0
        )
    }

    private fun setPopupWindowShadow(popupWindow: PopupWindow) {
        // 创建一个圆角矩形背景，模拟阴影效果
        val radii = floatArrayOf(20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f) // 设置圆角
        val drawable = ShapeDrawable(RoundRectShape(radii, null, null))


        // 设置阴影颜色和透明度
        drawable.paint.color = resources.getColor(android.R.color.white) // 背景色
        drawable.paint.setShadowLayer(
            10f,
            0f,
            5f,
            Color.GRAY
        ) // 阴影效果


        // 设置PopupWindow的背景
        popupWindow.setBackgroundDrawable(drawable)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }


    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("AlertDialog")
        builder.setMessage("Message")
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        dismissLoading()
    }

    /**
     * 加载中……弹框
     */
    fun showLoading() {
        showLoading(getString(R.string.default_loading))
    }

    /**
     * 加载提示框
     */
    fun showLoading(msg: String?) {
        dialogUtils.showLoading(msg)
    }

    /**
     * 加载提示框
     */
    fun showLoading(@StringRes res: Int) {
        showLoading(getString(res))
    }

    /**
     * 关闭提示框
     */
    fun dismissLoading() {
        dialogUtils.dismissLoading()
    }
}