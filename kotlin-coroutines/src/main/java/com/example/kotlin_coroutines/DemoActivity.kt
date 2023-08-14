package com.example.kotlin_coroutines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_coroutines.databinding.ActivityDemoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "DemoActivity-"
    }

    private lateinit var mBinding: ActivityDemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDemoBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.btnDelay.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                logI("点击了 ${mBinding.btnDelay.text.toString().trim()} 按钮")
                logI(getUserName(1))
            }

        }
    }


    suspend fun getUserName(userId: Int): String {
        delay(20000)
        return "Android 轮子哥"
    }

    private fun logI(msg: String) {
        mBinding.devInfoView.addDevInfo(msg)
        Log.i(TAG, msg)
    }

    private fun logW(msg: String) {
        mBinding.devInfoView.addWarnInfo(msg)
        Log.w(TAG, msg)
    }

    private fun logE(msg: String) {
        mBinding.devInfoView.addErrorInfo(msg)
        Log.e(TAG, msg)
    }
}


