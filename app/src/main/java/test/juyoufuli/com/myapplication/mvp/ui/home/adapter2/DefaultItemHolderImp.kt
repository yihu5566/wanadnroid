package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.ThirdViewUtil
import io.reactivex.Observable
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
    private var mAppComponent: AppComponent? = null
    private var mImageLoader: ImageLoader? = null//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    constructor(itemView: View) : super(itemView) {
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
        mImageLoader = mAppComponent!!.imageLoader()
        ThirdViewUtil.bindTarget(this, itemView)//绑定
    }


    override fun getData(data: ArticleBean) {

        if (data.type == 1) {
            textViewTop!!.text = "置顶"
            textViewTop!!.visibility = View.VISIBLE
        } else {
            if (data.niceDate.endsWith("小时前")) {
                textViewTop!!.text = "新"
                textViewTop!!.visibility = View.VISIBLE
            } else {
                textViewTop!!.visibility = View.GONE
            }
        }
//        tvPosition!!.setText("我是条目"+position)

        Observable.just(data.author)
                .subscribe { s -> mName!!.text = s }
        Observable.just(data.title)
                .subscribe { s -> mDesc!!.text = s }
        Observable.just(data.niceDate)
                .subscribe { s -> tvTime!!.text = s }
        ivFavoriteArticle!!.isChecked = data.collect

        ivFavoriteArticle!!.setOnClickListener {
            mOnViewClickListener!!.onViewClick(R.id.iv_favorite_article, position, data)
        }
    }


    override fun getDataHeader(any: ArrayList<BannerInfor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}