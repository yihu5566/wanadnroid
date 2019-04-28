package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.support.v7.widget.RecyclerView
import android.view.View
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor
import java.util.*

/**
 * @Author : dongfang
 * @Created Time : 2019-03-28  14:35
 * @Description:
 */
abstract class DefaultItemHolder<T> constructor(itemVIew: View) : RecyclerView.ViewHolder(itemVIew), View.OnClickListener {

    var mOnViewClickListener: OnViewClickListener? = null
    //条目点击时间加到基类
    var mOnItemClickListener: OnItemClickListener? = null

    var mOnHeaderItemClickListener: OnHeaderItemClickListener? = null

    init {
        itemVIew.setOnClickListener(this)
    }

    abstract fun getData(data: T)

    abstract fun getDataHeader(any: ArrayList<BannerInfor>)


    interface OnViewClickListener {
        fun onViewClick(viewid: Int, position: Int, data: ArticleBean)
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnHeaderItemClickListener {
        fun onHeaderItemClick(bannerInfor: BannerInfor)
    }

    override fun onClick(view: View) {
        if (mOnItemClickListener != null) {
            if (this.position > 0) {
                mOnItemClickListener!!.onItemClick(this.position)
            } else {
                mOnItemClickListener!!.onItemClick(this.position)

            }

        }
    }
}