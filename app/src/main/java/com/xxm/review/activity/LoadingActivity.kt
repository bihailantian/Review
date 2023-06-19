package com.xxm.review.activity

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R
import com.xxm.review.databinding.ActivityLoadingBinding
import com.xxm.review.utils.LoadingUtils

class LoadingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoadingBinding
    private val dialogUtils by lazy {
        LoadingUtils(this)
    }
    private var text: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.showLoading.setOnClickListener {
            showLoading()
            it.handler.postDelayed({
                dismissLoading()
            }, 5000)
        }
    
    }

    override fun onDestroy() {
        super.onDestroy()
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