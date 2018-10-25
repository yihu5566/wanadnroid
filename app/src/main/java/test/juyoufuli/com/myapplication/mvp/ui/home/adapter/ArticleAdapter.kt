package test.juyoufuli.com.myapplication.mvp.ui.home.adapter

import android.view.View

import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter

import javax.inject.Inject

import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class ArticleAdapter @Inject
constructor(infos: List<ArticleBean>) : DefaultAdapter<ArticleBean>(infos) {
    private var mChildClickListener: ArticleItemHolder.ChildClickListener? = null

    override fun getHolder(v: View, viewType: Int): BaseHolder<ArticleBean> {
        return ArticleItemHolder(
                v, mChildClickListener!!
        )
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.article_item
    }

    fun setChildClickListener(mChildClickListener: ArticleItemHolder.ChildClickListener) {
        this.mChildClickListener = mChildClickListener
    }
}
