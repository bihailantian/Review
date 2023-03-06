package com.xxm.review.activity.motionLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxm.review.R
import com.xxm.review.activity.motionLayout.adapter.TikTopAdapter
import com.xxm.review.domain.TikTopBean

/**
 *
 * @ClassName: MotionLayout1Activity
 *  motionLayout 简单实战
 */
class MotionLayout9Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout_9)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = TikTopAdapter(initData(), this)
    }

    private fun initData() = listOf(
            TikTopBean(R.drawable.item_head_1, "刘德华", "不要太在乎自己的长相,因为能力不会写在脸上。"),
            TikTopBean(
                    R.drawable.item_head_2,
                    "岳云鹏",
                    "过去的靠现在忘记,将来的靠现在努力,现在才最重要。"
            ),
            TikTopBean(
                    R.drawable.item_head_3,
                    "郭德纲",
                    "至于未来会怎样,要走下去才知道,反正路还很长,天总会亮。"
            ),
            TikTopBean(
                    R.drawable.item_head_4,
                    "迪丽热巴",
                    "成功之前我们要做应该做的事情,成功之后我们才可以做喜欢做的事情。"
            ),
            TikTopBean(R.drawable.item_head_2, "古力娜扎", "不是井里没有水，而是你挖的不够深"),
            TikTopBean(R.mipmap.no_data, "玛尔扎哈", "永远别放弃自己，哪怕所有人都放弃了你"),
            TikTopBean(R.drawable.item_head_3, "李宇春", "自己选择的路，跪着也要把它走完"),
            TikTopBean(
                    R.drawable.item_head_2,
                    "郭麒麟",
                    "不求与人相比，但求超越自己，要哭就哭出激动的泪水，要笑就笑出成长的性格!"
            ),
            TikTopBean(R.drawable.item_head_1, "刘德华", "不要太在乎自己的长相,因为能力不会写在脸上。"),
            TikTopBean(
                    R.drawable.item_head_2,
                    "岳云鹏",
                    "过去的靠现在忘记,将来的靠现在努力,现在才最重要。"
            ),
            TikTopBean(
                    R.drawable.item_head_3,
                    "郭德纲",
                    "至于未来会怎样,要走下去才知道,反正路还很长,天总会亮。"
            ),
            TikTopBean(
                    R.drawable.item_head_4,
                    "迪丽热巴",
                    "成功之前我们要做应该做的事情,成功之后我们才可以做喜欢做的事情。"
            ),
            TikTopBean(R.drawable.item_head_2, "古力娜扎", "不是井里没有水，而是你挖的不够深"),
            TikTopBean(R.mipmap.no_data, "玛尔扎哈", "永远别放弃自己，哪怕所有人都放弃了你"),
            TikTopBean(R.drawable.item_head_3, "李宇春", "自己选择的路，跪着也要把它走完"),
            TikTopBean(
                    R.drawable.item_head_2,
                    "郭麒麟",
                    "不求与人相比，但求超越自己，要哭就哭出激动的泪水，要笑就笑出成长的性格!"
            ),
    )
}