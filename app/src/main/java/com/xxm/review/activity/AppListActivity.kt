package com.xxm.review.activity

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xxm.review.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 */
class AppListActivity : AppCompatActivity() {

    class AppsItemInfo {
        var icon: Drawable? = null
        var label: String? = null
        var packageName: String? = null

        override fun toString(): String {
            return "AppsItemInfo(icon=$icon, label=$label, packageName=$packageName)"
        }


    }

    private val TAG = "AppListActivity-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        val gridview = findViewById<GridView>(R.id.gridview)

        Log.i(TAG, "onCreate")
        val appList = getAllApps(applicationContext, true)
        val dataList = arrayListOf<AppsItemInfo>()
        Log.i(TAG, "onCreate , appList.size=" + appList?.size)
        appList?.forEach { pinfo ->
            //Log.i(TAG, "pinfo=$pinfo")
            val shareItem = AppsItemInfo()
            shareItem.icon = packageManager.getApplicationIcon(pinfo.applicationInfo)
            shareItem.label = packageManager.getApplicationLabel(pinfo.applicationInfo).toString()
            shareItem.packageName = pinfo.applicationInfo.packageName
            dataList.add(shareItem)
        }
        /*Log.i(TAG, "===============================================")
        for (app in dataList){
            Log.i(TAG, "app=$app")
        }
        Log.i(TAG, "===============================================")*/
        val adapter = MyAdapter(this@AppListActivity, dataList)
        gridview.adapter = adapter
        gridview.setOnItemClickListener { parent, view, position, id ->
            Log.i(TAG, "setOnItemClickListener , item=" + dataList[position])
            dataList[position].packageName?.let {
                val intent = packageManager.getLaunchIntentForPackage(it)
                startActivity(intent)
            }

        }
    }

    private fun getAllApps(context: Context, isFilterSystem: Boolean): List<PackageInfo>? {
        val apps: MutableList<PackageInfo> = ArrayList()
        val pManager: PackageManager = context.packageManager
        val packList = pManager.getInstalledPackages(PackageManager.GET_GIDS)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        Log.i(TAG, "#######################################################################")
        for (i in packList.indices) {
            val pak = packList[i] as PackageInfo

            if (isFilterSystem) {
                if (pak.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM > 0) { //系统应用
                    Log.i(TAG, "pak=${pak}, firstInstallTime=${sdf.format(Date(pak.firstInstallTime))}, lastUpdateTime=${sdf.format(Date(pak.lastUpdateTime))}")
                    apps.add(pak)
                    pak.applicationInfo.uid
                    Log.i(TAG, "pak=${pak}, gids=${Arrays.toString(pak.gids)}")
                }
            } else {
                if (pak.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0) { //第三方应用
                    apps.add(pak)
                    //Log.i(TAG, "pak=${pak}, firstInstallTime=${sdf.format(Date(pak.firstInstallTime))}, lastUpdateTime=${sdf.format(Date(pak.lastUpdateTime))}")
                }
            }

        }
        //Log.i(TAG, "#######################################################################")
        return apps
    }

    private class MyAdapter(val context: Context, val list: List<AppsItemInfo>) : BaseAdapter() {
        //var inflater = LayoutInflater.from(context)
        //var list: List<AppsItemInfo> = data

        override fun getCount(): Int {
            //Log.i("AppListActivity-","list.size=${list.size}")
            return list.size
        }

        override fun getItem(position: Int): AppsItemInfo {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var contentView: View? = convertView
            val holder: ViewHolder
            if (contentView == null) {
                contentView = LayoutInflater.from(context).inflate(R.layout.item_app_list, null)
                holder = ViewHolder()
                holder.icon = contentView.findViewById(R.id.apps_image) as ImageView
                holder.label = contentView
                        .findViewById(R.id.apps_textview)
                contentView.tag = holder
            } else {
                holder = contentView.tag as ViewHolder
            }
            //Log.i("AppListActivity-","position=$position, icon=${list?.get(position)?.icon}, label=${list?.get(position)?.label.toString()}")
            holder.icon?.setImageDrawable(list[position].icon)
            holder.label?.text = list[position]?.label.toString()
            //holder.label?.text = list?.get(position)?.packageName
            return contentView!!
        }
    }

    private class ViewHolder {
        var icon: ImageView? = null
        var label: TextView? = null
    }
}