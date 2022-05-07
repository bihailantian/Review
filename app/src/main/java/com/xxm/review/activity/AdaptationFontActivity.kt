package com.xxm.review.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R

/**
 * 字体大小适配
 * 参考：https://www.jianshu.com/p/2fdc97ae74a8
 */
class AdaptationFontActivity : AppCompatActivity() {

    /*
    动态编码方式
    使用 TextViewCompat 的setAutoSizeTextTypeWithDefaults()方法设置TextView是否支持自动改变字体大小，setAutoSizeTextTypeUniformWithConfiguration()方法设置最小字体大小、最大字体大小与缩放粒度。如下所示：
    val tvText: TextView = findViewById(R.id.tv_text)
    TextViewCompat.setAutoSizeTextTypeWithDefaults(tvText, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvText, 10, 18, 1, TypedValue.COMPLEX_UNIT_SP)
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptation_font)


    }
}