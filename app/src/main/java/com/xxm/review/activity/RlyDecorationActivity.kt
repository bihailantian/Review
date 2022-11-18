package com.xxm.review.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxm.review.R
import com.xxm.review.databinding.ActivityRlyDecorationBinding
import com.xxm.review.databinding.ItemCityBinding
import com.xxm.review.domain.City
import com.xxm.review.utils.CityUtil
import com.xxm.review.view.StickyDecoration

/**
 * 利用RecyclerView.ItemDecoration实现的悬浮效果
 */
class RlyDecorationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRlyDecorationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRlyDecorationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val cityList = CityUtil.getCityList()
        binding.recyclerView.adapter = DecorationAdapter(this, cityList)
        //分割线
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        //粘性布局
        binding.recyclerView.addItemDecoration(StickyDecoration(this, object : StickyDecoration.DecorationCallback {
            override fun getData(position: Int): String? {
                return cityList[position].province
            }
        })
        )
    }


    private class DecorationAdapter(private val context: Context, val dataList: List<City>) : RecyclerView.Adapter<DecorationAdapter.ViewHolder>() {
        class ViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val city = dataList[position]
            when (position % 5 + 1) {
                1 -> {
                    holder.binding.ivCity.setImageResource(R.mipmap.subject1)
                    holder.binding.llBg.setBackgroundColor(ContextCompat.getColor(context, R.color.bg1))
                }
                2 -> {
                    holder.binding.ivCity.setImageResource(R.mipmap.subject2)
                    holder.binding.llBg.setBackgroundColor(ContextCompat.getColor(context, R.color.bg2))
                }
                3 -> {
                    holder.binding.ivCity.setImageResource(R.mipmap.subject3)
                    holder.binding.llBg.setBackgroundColor(ContextCompat.getColor(context, R.color.bg3))
                }
                4 -> {
                    holder.binding.ivCity.setImageResource(R.mipmap.subject4)
                    holder.binding.llBg.setBackgroundColor(ContextCompat.getColor(context, R.color.bg4))
                }
                else -> {
                    holder.binding.ivCity.setImageResource(R.mipmap.subject5)
                    holder.binding.llBg.setBackgroundColor(ContextCompat.getColor(context, R.color.bg5))
                }
            }
            holder.binding.tvCity.text = city.name
        }


    }
}