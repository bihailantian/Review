package com.xxm.review.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.databinding.ActivityTestDialogBinding
import com.xxm.review.global.RuntimeInfo


class TestDialogActivity : AppCompatActivity() {
    private val TAG = "TestDialogActivity-"
    private lateinit var mBinding: ActivityTestDialogBinding

    var mAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTestDialogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        Log.d(TAG, "onCreate")

        mBinding.btnOpenDialogActivity.setOnClickListener {
            val intent = Intent(this@TestDialogActivity, DialogActivity::class.java)
            startActivity(intent)
        }

        mBinding.btnOpenDialog.setOnClickListener {
            showDialog()
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
        val context = if (RuntimeInfo.topActivity != null) {
            Log.d(TAG, "use  RuntimeInfo.topActivity")
            RuntimeInfo.topActivity!!
        } else {
            Log.d(TAG, "use this")
            this
        }
        val builder = AlertDialog.Builder(context)
        builder.setTitle("AlertDialog")
        builder.setMessage("Message")
        mAlertDialog = builder.create()
        mAlertDialog?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        mAlertDialog?.dismiss()
    }


}