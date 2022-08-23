package com.xuxm.chipdemo

import android.os.Bundle
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.xuxm.chipdemo.databinding.ActivityChipBinding


/**
 * 功用：Chip 和  ChipGroup 的使用
 * 其他：
 * 1、参考1
 * Demo参考：https://github.com/firatkarababa/AndroidMaterialChips
 * 对应文章：https://medium.com/material-design-in-action/chips-material-components-for-android-46001664a40f
 *
 * 2、参考2
 * https://material.io/develop/android/components/chip/
 * https://blog.csdn.net/weixin_42046829/article/details/110220160
 *
 */
class ChipActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnKeyListener {

    private lateinit var mActivity: ChipActivity

    private var mPreSelectionEnd: Int = 0

    private val TAG = ChipActivity::class.java.simpleName
    private lateinit var mBinding: ActivityChipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChipBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mActivity = this

        initListener()
        dynamicAddChip()
        addChipDrawableToEt()
    }

    /**
     * 功用：动态的添加ChipDrawable到EditText中
     * 说明：
     * 注意，
     * 1、这一块目前还有缺陷，假设是一直在末尾输入然后删除的话，没有什么异常；但是，如果从中间插入，此时就不知道
     * 该怎么计算和获取需要设置给chip的字符了
     * 2、在执行删除事件时，无法给 ChipDrawable/ClostIcon 设置点击。只能通过软键盘的 删除 按键删除
     * 3、在使用软键盘的 删除 按键执行删除操作时，可以一次直接删除一个 chipDrawable的span. 暂时没找到内部实现的代码
     * 4、此外，chip 中包含的文本不能超过一行，如果超过一行之后，chipDrawable 将展示不全，所以，此处的 EditText 应用了singleLine=true
     */
    private fun addChipDrawableToEt() {

        //直接以 Span的形式将 chipDrawable 加入到 EditText中，这样看着很好，但是，ChipDrawable 中closeIcon的点击事件没法实现啊
        mBinding.btApplyChip.setOnClickListener { view ->
            val chipDrawable = ChipDrawable.createFromResource(mActivity, R.xml.chip_drawable_1)
            val text = mBinding.editText.text
            val newInputText = text.substring(mPreSelectionEnd, text.length)
            chipDrawable.text = newInputText
            chipDrawable.setBounds(0, 0, chipDrawable.intrinsicWidth, chipDrawable.intrinsicHeight)

            val span = ImageSpan(chipDrawable)
            text.setSpan(span, mPreSelectionEnd, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            mBinding.editText.setOnKeyListener(this)

            mPreSelectionEnd = text.length
        }
    }

    private fun initListener() {
        initChipClickListener()
        initChipCheckedListener()
        initClostIconClickListener()
        initChipGroupCheckedListener()
    }


    private fun initChipClickListener() {
        //点击监听
        //让当前界面实现监听接口
        mBinding.chipNormal1.setOnClickListener(this)
        //构造匿名监听
        mBinding.chipAction.setOnClickListener {
            Toast.makeText(mActivity, "Chip被点击了", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChipGroupCheckedListener() {
        //ChipGroup中设置选中监听-- 只有单选的chipGroup才可以使用
        mBinding.chipGroup2.setOnCheckedChangeListener { chipGroup, selectedId ->
            var hintStr = ""
            when (selectedId) {
                R.id.chipInGroup2_1 -> hintStr = "被选中的是 chipInGroup2_1 "
                R.id.chipInGroup2_2 -> hintStr = "被选中的是 chipInGroup2_2 "
                R.id.chipInGroup2_3 -> hintStr = "被选中的是 chipInGroup2_3 "
                else -> {
                    hintStr = "没有选中任何chip__手动设置一个作为默认选中"
                    mBinding.chipInGroup21.isChecked = true
                }
            }
            Toast.makeText(mActivity, hintStr, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initClostIconClickListener() {
        //关闭按钮的点击监听——closeIcon 没有id，所以必须需要构造匿名监听
        mBinding.chipEntry.setOnCloseIconClickListener {
            Toast.makeText(mActivity, "ClostIcon被点击了", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChipCheckedListener() {
        //选中监听
        //因为 action style下，checkable 是默认false的，所以，该监听无效；
        mBinding.chipAction.setOnCheckedChangeListener(this)
        mBinding.chipChoice.setOnCheckedChangeListener(this)
        mBinding.chipEntry.setOnCheckedChangeListener(this)
        mBinding.chipFilter.setOnCheckedChangeListener { buttonView, isChecked ->
            var hintStr = ""
            if (isChecked) {
                hintStr = "被选中了"
            } else {
                hintStr = "取消选中了"
            }
            Toast.makeText(mActivity, hintStr, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        //v?.id 表示，v不为空时才去调用 getId
        val id = v?.id
        when (id) {
            R.id.chip_normal1 ->
                Toast.makeText(mActivity, "Chip被点击了", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        var hintStr = ""
        if (isChecked) {
            hintStr = "被选中了"
        } else {
            hintStr = "取消选中了"
        }
        Toast.makeText(mActivity, hintStr, Toast.LENGTH_SHORT).show()
    }

    /**
     * 时间：2018/8/16 下午9:20
     * 功用：使用代码动态的添加chip
     * 说明：
     */
    private fun dynamicAddChip() {

        //添加到 整个页面的末尾
        val chip = Chip(mActivity)
        chip.text = "这是代码添加的chip"
        mBinding.llParent.addView(chip)

        //添加到 chipGroup
        val chip2 = Chip(mActivity)
        chip2.text = "这是代码添加的chip2"
        mBinding.chipGroup1.addView(chip2)
    }

    /**
     * 功用：软键盘删除事件
     * 说明：如果触发了软键盘的删除按钮事件，此时，重置 mPreSelectionEnd
     */
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_DEL == keyCode) {
            val text = mBinding.editText.text
            if (mPreSelectionEnd > text.length) {
                //说明有 chipDrawable 被删除了
                mPreSelectionEnd = text.length
            }
        }

        return false
    }
}


