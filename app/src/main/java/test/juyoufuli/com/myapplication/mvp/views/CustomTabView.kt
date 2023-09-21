package test.juyoufuli.com.myapplication.mvp.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import test.juyoufuli.com.myapplication.R


/**
 * @Author : dongfang
 * @Created Time : 2021-12-06  11:17
 * @Description:
 */
public class CustomTabView : LinearLayout, View.OnClickListener {
    private var mTabViews //保存TabView
            : MutableList<View>? = null
    private var mTabs // 保存Tab
            : MutableList<Tab>? = null
    private var mOnTabCheckListener: OnTabCheckListener? = null
    fun setOnTabCheckListener(onTabCheckListener: OnTabCheckListener?) {
        mOnTabCheckListener = onTabCheckListener
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        mTabViews = ArrayList()
        mTabs = ArrayList()
    }

    /**
     * 添加Tab
     * @param tab
     */
    fun addTab(tab: Tab) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab_item, null)
        val textView = view.findViewById(R.id.custom_tab_text) as TextView
        val imageView: ImageView = view.findViewById(R.id.custom_tab_icon) as ImageView
        imageView.setImageResource(tab.mIconNormalResId)
        textView.text = tab.mText
        textView.setTextColor(tab.mNormalColor)
        view.tag = mTabViews!!.size
        view.setOnClickListener(this)
        mTabViews!!.add(view)
        mTabs!!.add(tab)
        addView(view)
    }

    /**
     * 设置选中Tab
     * @param position
     */
    fun setCurrentItem(position: Int) {
        var mPosition = position
        if (position >= mTabs!!.size || position < 0) {
            mPosition = 0
        }
        mTabViews!![mPosition].performClick()
        updateState(mPosition)
    }

    /**
     * 更新状态
     * @param position
     */
    private fun updateState(position: Int) {
        for (i in mTabViews!!.indices) {
            val view: View = mTabViews!![i]
            val textView = view.findViewById(R.id.custom_tab_text) as TextView
            val imageView: ImageView = view.findViewById(R.id.custom_tab_icon) as ImageView
            if (i == position) {
                imageView.setImageResource(mTabs!![i].mIconPressedResId)
                textView.visibility = View.VISIBLE
                textView.setTextColor(mTabs!![i].mSelectColor)
                view.isSelected = true
            } else {
                imageView.setImageResource(mTabs!![i].mIconNormalResId)
                textView.visibility = View.GONE
                view.isSelected = false
            }
        }
    }

    override fun onClick(v: View) {
        val position = v.tag as Int
        if (mOnTabCheckListener != null) {
            mOnTabCheckListener!!.onTabSelected(v, position)
        }
        updateState(position)
    }

    interface OnTabCheckListener {
        fun onTabSelected(v: View?, position: Int)
    }

    class Tab {
        var mIconNormalResId = 0
        var mIconPressedResId = 0
        var mNormalColor = 0
        var mSelectColor = 0
        var mText: String? = null
        fun setText(text: String?): Tab {
            mText = text
            return this
        }

        fun setNormalIcon(res: Int): Tab {
            mIconNormalResId = res
            return this
        }

        fun setPressedIcon(res: Int): Tab {
            mIconPressedResId = res
            return this
        }

        fun setColor(color: Int): Tab {
            mNormalColor = color
            return this
        }

        fun setCheckedColor(color: Int): Tab {
            mSelectColor = color
            return this
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mTabViews != null) {
            mTabViews!!.clear()
        }
        if (mTabs != null) {
            mTabs!!.clear()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 调整每个Tab的大小
        for (i in mTabViews!!.indices) {
            val view: View = mTabViews!![i]
            val width = resources.displayMetrics.widthPixels / mTabs!!.size
            val params = LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT)
            view.layoutParams = params
        }
    }
}