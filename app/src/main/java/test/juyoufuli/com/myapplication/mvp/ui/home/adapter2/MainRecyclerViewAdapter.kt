package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.content.Context
import android.view.ViewGroup
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean

/**
 * @Author : dongfang
 * @Created Time : 2019-03-28  15:08
 * @Description:
 */
class MainRecyclerViewAdapter constructor(
    var context: Context,
    articleBean: ArrayList<ArticleBean>
) : BaseRecyclerViewAdapter<ArticleBean>(context) {

    private val TYPE_HEADER = 1001

    init {
        mList = articleBean
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultItemHolder<ArticleBean> {
        return if (viewType == TYPE_HEADER) {
            var headerMainBanner = mLayoutInflater.inflate(R.layout.header_main_banner, null)
            HeaderItemHolderImp(headerMainBanner, mBannerList, context)
        } else {
            var inflate = mLayoutInflater.inflate(R.layout.article_item, parent, false)
            DefaultItemHolderImp(inflate)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return super.getItemViewType(position)
    }

}