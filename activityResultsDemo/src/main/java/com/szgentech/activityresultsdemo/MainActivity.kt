package com.szgentech.activityresultsdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //方式一
        findViewById<Button>(R.id.btn_next_page).setOnClickListener {
            launcherActivity.launch(Intent(this@MainActivity,SecondActivity::class.java).apply {
                putExtra("name",MainActivity::class.java.canonicalName)
            })
        }

        //方式二
        findViewById<Button>(R.id.btn_next_page2).setOnClickListener {
            // 开启页面跳转
            myActivityLauncher.launch("Hello,技术最TOP")
        }
    }

    private val launcherActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        val code = it.resultCode
        val data = it.data?.getStringExtra("result")

        Log.d("MainActivity","code=$code, data=$data")
    }


    private val myActivityLauncher = registerForActivityResult(MyActivityResultContract()){result ->
        Toast.makeText(applicationContext,result,Toast.LENGTH_SHORT).show()
        Log.d("MainActivity", "回传数据：$result")
    }
}