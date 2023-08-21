package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.subscribe
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor
import java.util.*

/**
 * @Author : dongfang
 * @Created Time : 2019-03-28  14:36
 * @Description:
 */
class DefaultItemHolderImp : DefaultItemHolder<ArticleBean> {
    @JvmField
    @BindView(R.id.tv_chapterName)
    internal var mName: TextView? = null

    @JvmField
    @BindView(R.id.tv_position)
    internal var tvPosition: TextView? = null

    @JvmField
    @BindView(R.id.tv_desc)
    internal var mDesc: TextView? = null

    @JvmField
    @BindView(R.id.tv_time)
    internal var tvTime: TextView? = null

    @JvmField
    @BindView(R.id.iv_favorite_article)
    internal var ivFavoriteArticle: CheckBox? = null

    @JvmField
    @BindView(R.id.tv_top_tag)
    internal var textViewTop: TextView? = null

    constructor(itemView: View) : super(itemView) {
        ButterKnife.bind(itemView)
    }


    override fun getData(data: ArticleBean) {

        if (data.type == 1) {
            textViewTop?.text = "置顶"
            textViewTop?.visibility = View.VISIBLE
        } else {
            if (data.niceDate.endsWith("小时前")) {
                textViewTop?.text = "新"
                textViewTop?.visibility = View.VISIBLE
            } else {
                textViewTop?.visibility = View.GONE
            }
        }
//        tvPosition?.setText("我是条目"+position)

//        Observable.just(data.author)
//            .subscribe { s -> mName?.text = s }
//        Observable.just(data.title)
//            .subscribe { s -> mDesc?.text = s }
//        Observable.just(data.niceDate)
//            .subscribe { s -> tvTime?.text = s }
        ivFavoriteArticle?.isChecked = data.collect

        ivFavoriteArticle?.setOnClickListener {
            mOnViewClickListener?.onViewClick(R.id.iv_favorite_article, position, data)
        }
    }


    override fun getDataHeader(any: ArrayList<BannerInfor>) {
    }


}