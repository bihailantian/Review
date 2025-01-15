package com.xxm.review.activity

import android.os.Bundle
import android.util.Log
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