package com.xxm.review.activity.motionLayout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxm.review.R
import com.xxm.review.domain.TikTopBean

/**
 *
 *
 */
class TikTopAdapter(
        val data: List<TikTopBean>, val context: Context
) : RecyclerView.Adapter<TikTopAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        val imageHead: ImageView = itemView.findViewById(R.id.imageHead)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tik_top_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvTitle.text = item.title
        holder.tvComment.text = item.comment
        holder.imageHead.setImageResource(item.url)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}