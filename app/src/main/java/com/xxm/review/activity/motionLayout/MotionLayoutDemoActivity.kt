package com.xxm.review.activity.motionLayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R

/**
 *
 * @ClassName: MotionLayoutDemoActivity
 *  MotionLayout布局测试
 *  官方文档:https://developer.android.com/training/constraint-layout/motionlayout#additional_motionlayout_attributes
 */
class MotionLayoutDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_demo_layout)
    }


    /*
     * 作者:android 超级兵
     * 创建时间: 12/28/21 2:54 PM
     * 
     */
    fun onMotion1Click(view: View) {
        startActivity(Intent(this, MotionLayout1Activity::class.java))
    }

    fun onMotion2Click(view: View) {
        startActivity(Intent(this, MotionLayout2Activity::class.java))
    }

    fun onMotion3Click(view: View) {
        startActivity(Intent(this, MotionLayout3Activity::class.java))
    }

    fun onMotion4Click(view: View) {
        startActivity(Intent(this, MotionLayout4Activity::class.java))
    }

    fun onMotion5Click(view: View) {
        startActivity(Intent(this, MotionLayout5Activity::class.java))
    }

    fun onMotion6Click(view: View) {
        startActivity(Intent(this, MotionLayout6Activity::class.java))
    }

    fun onMotion7Click(view: View) {
        startActivity(Intent(this, MotionLayout7Activity::class.java))
    }

    fun onMotion8Click(view: View) {
        startActivity(Intent(this, MotionLayout8Activity::class.java))
    }

    fun onMotion9Click(view: View) {
        startActivity(Intent(this, MotionLayout9Activity::class.java))
    }

    /*
     *   KeyCycle
     */
    fun onMotion10Click(view: View) {
        startActivity(Intent(this, MotionLayout10Activity::class.java))
    }

    /*
     *  KeyTrigger
     */
    fun onMotion11Click(view: View) {
        startActivity(Intent(this, MotionLayout11Activity::class.java))
    }

    /*
     *  KeyTimeCycle
     */
    fun onMotion12Click(view: View) {
        startActivity(Intent(this, MotionLayout12Activity::class.java))
    }

    /*
     *  Easing
     */
    fun onMotion13Click(view: View) {
        startActivity(Intent(this, MotionLayout13Activity::class.java))
    }
}