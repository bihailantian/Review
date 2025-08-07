package com.xxm.review.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R
import com.xxm.review.utils.ALog


class DialogActivity : AppCompatActivity() {

    private var isNeedFinishOnResume = false
    private lateinit var tvMessage: TextView
    private lateinit var tvCancel: TextView
    private lateinit var tvOk: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        ALog.d("DialogActivity", "onCreate")

        tvMessage = findViewById(R.id.tvMessage)
        tvCancel = findViewById(R.id.tvCancel)
        tvOk = findViewById(R.id.tvOk)
        tvMessage.text = "message"
        /*intent?.let {
            val permissionName = it.getStringExtra("permissionName")
            //tvMessage.text = getString(R.string.permission_dialog_msg, permissionName)
           
        } ?: kotlin.run {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }*/

        tvCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        tvOk.setOnClickListener {
            isNeedFinishOnResume = true
            // 权限未被授权，跳转到权限授权页面
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)

        }


    }

    override fun onResume() {
        super.onResume()
        ALog.d("DialogActivity", "onResume")
        if (isNeedFinishOnResume) {
            isNeedFinishOnResume = false
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ALog.d("DialogActivity", "onDestroy")
    }
}