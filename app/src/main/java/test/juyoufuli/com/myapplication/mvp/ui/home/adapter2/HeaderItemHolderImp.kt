package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.content.Context
import android.content.Intent
import android.view.View
import cn.bingoogolapple.bgabanner.BGABanner
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.ArmsUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity

/**
 * @Author : dongfang
 * @Created Time : 2019-03-28  14:36
 * @Description:
 */
class HeaderItemHolderImp(
    itemVIew: View,
    var mBannerList: List<BannerInfor>?,
    var context: Context
) : DefaultItemHolder<ArticleBean>(itemVIew), View.OnClickListener {
    var bannerGuideContent: BGABanner? = null
    var mAppComponent: AppComponent? = null
    var mImageLoader: ImageLoader? = null//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架nt: BGABanner? = null

    init {
        bannerGuideContent = itemVIew.findViewById(R.id.banner_guide_content)
        mBannerList?.let { initBanner(it) }

        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
        mImageLoader = mAppComponent?.imageLoader()
    }

    override fun getData(data: ArticleBean) {
    }

    override fun getDataHeader(any: ArrayList<BannerInfor>) {
//        LogUtils.d("我来到了头部的holder中啦")
        mBannerList = any
        if (mBannerList != null) {
            initBanner(any)
        }
    }

    private fun initBanner(mBannerList: List<BannerInfor>) {
        val imagePath = ArrayList<String>()
        val imageTitle = ArrayList<String>()
        for ((_, _, imagePath1, _, _, title) in mBannerList) {
            imagePath.add(imagePath1)
            imageTitle.add(title)
        }
        bannerGuideContent?.setData(imagePath, imageTitle)
        bannerGuideContent?.setDelegate { banner, itemView, model, position ->

            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("link", mBannerList[position].url)
            intent.putExtra("title", mBannerList[position].title)
            context.startActivity(intent)
        }

        bannerGuideContent?.setAdapter { bgaBanner: BGABanner, view: View, model: Any?, i: Int ->
//            ImageLoaderUtils.loadImage(view as ImageView?, model, context)
        }
    }

}