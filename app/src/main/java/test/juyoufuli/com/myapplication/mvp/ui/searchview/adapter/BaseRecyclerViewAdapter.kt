package test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import java.util.ArrayList

abstract class BaseRecyclerViewAdapter<T>(private var mContext: Context) : RecyclerView.Adapter<SearchItemHolder>() {
    var mList: ArrayList<T>? = ArrayList()
    var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    var mOnItemClickListener: OnItemClickListener<T>? = null

    var list: ArrayList<T>?
        get() = mList
        set(list) {
            mList = list

            notifyDataSetChanged()
        }

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener<T>) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    fun clearList() {
        mList = ArrayList()
        notifyDataSetChanged()
    }

    fun addList(list: List<T>?) {
        if (list == null) return
        if (mList == null) mList = ArrayList()
        for (t in list) {
            mList!!.add(t)
        }
        notifyDataSetChanged()
    }

    fun appendData(list: List<T>?) {
        if (list != null && !list.isEmpty()) {
            this.mList!!.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun removeItem(`object`: T?) {
        if (mList == null || `object` == null)
            return
        mList!!.remove(`object`)
        notifyDataSetChanged()
    }

    fun removeItem(pos: Int) {
        if (mList == null || pos > mList!!.size)
            return
        mList!!.removeAt(pos)
        notifyDataSetChanged()
    }

    fun addFirst(item: T) {
        if (mList == null) mList = ArrayList()
        mList!!.add(0, item)
        notifyDataSetChanged()
    }

    fun addAt(index: Int, item: T) {
        if (mList == null) {
            mList = ArrayList()
        }
        mList!!.add(index, item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    fun getItem(position: Int): T? {
        if (mList == null) {
            return null
        }
        return if (position < 0 || position >= mList!!.size) {
            null
        } else mList!![position]
//        return mList == null ? null : mList.get(position);
    }

    interface OnItemClickListener<T> {
        fun onItemClick(clickId: Int, position: Int, item: T)
    }
}
