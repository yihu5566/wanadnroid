package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo

abstract class BaseRecyclerViewAdapter<T>(private var mContext: Context) :
    RecyclerView.Adapter<DefaultItemHolder<T>>() {
    var mList: ArrayList<T>? = ArrayList()
    var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    var mBannerList: ArrayList<BannerInfo>? = null

    var list: ArrayList<T>?
        get() = mList
        set(list) {
            mList = list

            notifyDataSetChanged()
        }

    var mOnItemClickListener: DefaultItemHolder.OnItemClickListener? = null
    var mChildClickListener: DefaultItemHolder.OnViewClickListener? = null
    var mOnHeaderItemClickListener: DefaultItemHolder.OnHeaderItemClickListener? = null


    fun setChildClickListener(mChildClickListener: DefaultItemHolder.OnViewClickListener) {
        this.mChildClickListener = mChildClickListener
    }

    fun setOnItemClickListener(mOnItemClickListener: DefaultItemHolder.OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    fun setOnHeaderItemClickListener(mOnItemClickListener: DefaultItemHolder.OnHeaderItemClickListener) {
        this.mOnHeaderItemClickListener = mOnItemClickListener
    }

    override fun onBindViewHolder(defaultItemHolder: DefaultItemHolder<T>, position: Int) {
        if (defaultItemHolder is DefaultItemHolderImp) {
            defaultItemHolder.getData(mList!![position - 1])
        } else {
            if (mBannerList == null) return
            defaultItemHolder.getDataHeader(mBannerList!!)
        }

        defaultItemHolder.mOnItemClickListener = mOnItemClickListener
        defaultItemHolder.mOnViewClickListener = mChildClickListener
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
        return mList!!.size + 1
    }

    fun getItem(position: Int): T? {
        if (mList == null) {
            return null
        }
        return if (position < 0 || position >= mList!!.size) null else mList!![position]
//        return mList == null ? null : mList.get(position);
    }

}
