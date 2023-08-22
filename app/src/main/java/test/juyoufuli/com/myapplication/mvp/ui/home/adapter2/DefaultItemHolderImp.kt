package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.view.View
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.databinding.ArticleItemBinding
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo

/**
 * @Author : dongfang
 * @Created Time : 2019-03-28  14:36
 * @Description:
 */
class DefaultItemHolderImp(itemView: View) : DefaultItemHolder<ArticleBean>(itemView) {
    val itemBinding: ArticleItemBinding

    init {
        itemBinding = ArticleItemBinding.bind(itemView)
    }


    override fun getData(data: ArticleBean) {

        if (data.type == 1) {
            itemBinding.tvTopTag.text = "置顶"
            itemBinding.tvTopTag.visibility = View.VISIBLE
        } else {
            if (data.niceDate.endsWith("小时前")) {
                itemBinding.tvTopTag.text = "新"
                itemBinding.tvTopTag.visibility = View.VISIBLE
            } else {
                itemBinding.tvTopTag.visibility = View.GONE
            }
        }

        runBlocking {
            flowOf(data.author).collectLatest {
                itemBinding.tvChapterName.text = it
            }
        }
        itemBinding.tvPosition.text = data.title
        itemBinding.tvTime.text = data.niceDate
        itemBinding.ivFavoriteArticle.isChecked = data.collect

        itemBinding.ivFavoriteArticle.setOnClickListener {
            mOnViewClickListener?.onViewClick(R.id.iv_favorite_article, position, data)
        }
    }


    override fun getDataHeader(any: ArrayList<BannerInfo>) {
    }


}