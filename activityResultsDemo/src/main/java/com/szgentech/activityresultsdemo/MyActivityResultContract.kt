package com.szgentech.activityresultsdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class MyActivityResultContract: ActivityResultContract<String, String>(){
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context,SecondActivity::class.java).apply {
            putExtra("name",input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val data = intent?.getStringExtra("result")
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }



}