package com.xxm.review.global

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics

/**
 * @author benzly
 * @date 2019-12-22
 */
object RuntimeInfo {
    @JvmStatic
    lateinit var sAppContext: Context
    var uid: Long? = null
    var name: String? = null
    var password: String? = null
    var isLogin = false
    var gpsJd: String? = null
    var gpsWd: String? = null
    var gpsAddr: String? = null
    var imei: String? = null
    var sn: String? = null
    var cameraIsUsing = false
    var displayMetrics: DisplayMetrics? = null
    var topActivity: Activity? = null
    var uDiskMode = false //U盘模式
    var versionCode: String? = null
    var versionName: String? = null
    var batteryPower: Int = -1 //电池电量
    var recordVideoSizeConfig: String = "" //录像分辨率

    //隐秘录制
    var isSecretRecording = false

    //tts模式
    var isTTSMode = true

    //正在录像
    var isTakingVideo = false

    //横屏录像角度,-1:默认
    var landscapeRecordVideoAngle = -1

    //横屏录像
    var isLandscapeRecordVideoMode = false

    fun getPackageName(): String? {
        return sAppContext.packageName
    }

    @SuppressLint("MissingPermission")
    fun getIMEI(): String? {
        sAppContext.let {
            val telephonyMgr = it.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyMgr.imei
            } else {
                telephonyMgr.deviceId
            }
        }
        return ""
    }

    fun isDebug(): Boolean {
        sAppContext.applicationInfo?.let {
            return (it.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        }
        return false
    }


}