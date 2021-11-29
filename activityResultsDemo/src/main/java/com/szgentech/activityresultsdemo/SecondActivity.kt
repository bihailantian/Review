package com.szgentech.activityresultsdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val name = intent.getStringExtra("name")
        Log.d("MainActivity2", "name=$name")
        Toast.makeText(this, "name=$name", Toast.LENGTH_SHORT).show()

        findViewById<Button>(R.id.btn_finish).setOnClickListener {
            val intent = Intent().apply {
                putExtra("result", "Hello，依然范特西稀，我是回传的数据！")
            }
            setResult(Activity.RESULT_OK, intent)
            finish()

        }
    }
}