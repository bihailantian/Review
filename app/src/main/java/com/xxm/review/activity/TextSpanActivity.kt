package com.xxm.review.activity

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.SuperscriptSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R

class TextSpanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_span)

        val tvSpan = findViewById<TextView>(R.id.tv_span)

        val string = SpannableString("Text with underline span")
        string.setSpan(UnderlineSpan(), 10, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        val textString = SpannableString("你有新消息了●")
        textString.apply {
            setSpan(ForegroundColorSpan(Color.RED), textString.length - 1, textString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(SuperscriptSpan(), textString.length - 1, textString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }



        tvSpan.text = textString
    }
}
