package test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter

import android.content.Context
import android.text.Html
import android.view.ViewGroup
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class SearchAdapter(context: Context) : BaseRecyclerViewAdapter<ArticleBean>(context) {

    override fun onBindViewHolder(holder: SearchItemHolder, position: Int) {
        val mHolder = holder
        val data = list!![position]
        mHolder.mName.text = data.author
        mHolder.mDesc.text = Html.fromHtml(data.title)
        mHolder.tvTime.text = data.niceDate

        mHolder.root.setOnClickListener { mOnItemClickListener!!.onItemClick(R.id.root, position, data) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemHolder {
        val inflate = mLayoutInflater.inflate(R.layout.article_item, null)
        return SearchItemHolder(inflate)
    }


}
