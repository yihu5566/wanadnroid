package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.ImageLoaderUtils
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity

/**
 * @Author : dongfang
 * @Created Time : 2019-03-28  14:36
 * @Description:
 */
class HeaderItemHolderImp(
    itemVIew: View,
    var mBannerList: List<BannerInfo>?,
    var context: Context
) : DefaultItemHolder<ArticleBean>(itemVIew), View.OnClickListener {
    var bannerGuideContent: BGABanner? = null

    init {
        bannerGuideContent = itemVIew.findViewById(R.id.banner_guide_content)
        mBannerList?.let { initBanner(it) }
    }

    override fun getData(data: ArticleBean) {
    }

    override fun getDataHeader(any: List<BannerInfo>) {
//        LogUtils.d("我来到了头部的holder中啦")
        mBannerList = any
        if (mBannerList != null) {
            initBanner(any)
        }
    }

    private fun initBanner(mBannerList: List<BannerInfo>) {
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

        bannerGuideContent?.setAdapter { _: BGABanner, view: View, model: Any?, index: Int ->
            ImageLoaderUtils.loadImage(view as ImageView, model as String, context)
        }
    }

}