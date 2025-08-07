package com.xxm.review.utils

import android.os.Build

class DeviceInfoUtils {
    private val TAG = "DeviceInfoUtils"

    class DeviceInfo(info: String?, desc: String?)

    fun printInfo() {
        val infos = ArrayList<DeviceInfo>()
        infos.add(DeviceInfo(Build.VERSION.SDK_INT.toString(), "SDK 版本号"))
        //infos.add(DeviceInfo(Build.VERSION,"Android 版本"))  
        infos.add(DeviceInfo(Build.ID, "构建标识符"))
        infos.add(DeviceInfo(Build.TAGS, "系统构建标签"))
        infos.add(DeviceInfo(Build.TYPE, "系统构建类型"))
        infos.add(DeviceInfo(Build.FINGERPRINT, "完整系统构建指纹"))
        //infos.add(DeviceInfo(ro.build.description,"系统版本描述"))  
        infos.add(DeviceInfo(Build.BOARD, "主板标识"))
        infos.add(DeviceInfo(Build.MANUFACTURER, "厂商名称"))
        infos.add(DeviceInfo(Build.BRAND, "品牌信息"))
        infos.add(DeviceInfo(Build.MODEL, "设备型号"))
        infos.add(DeviceInfo(Build.PRODUCT, "产品代号"))
        infos.add(DeviceInfo(Build.DEVICE, "设备名称"))
        infos.add(DeviceInfo(Build.HARDWARE, "硬件平台"))
        infos.add(DeviceInfo(Build.HOST, "构建主机名"))

    }
}
