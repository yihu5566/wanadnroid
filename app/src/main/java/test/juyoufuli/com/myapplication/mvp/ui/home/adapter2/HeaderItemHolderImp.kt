package test.juyoufuli.com.myapplication.mvp.ui.home.adapter2

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import cn.bingoogolapple.bgabanner.BGABanner
import com.we.jetpackmvvm.ext.nav
import com.we.jetpackmvvm.ext.navigateAction
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.ImageLoaderUtils
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo

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
    private val bannerGuideContent: BGABanner

    init {
        bannerGuideContent = itemVIew.findViewById(R.id.banner_guide_content)
        mBannerList?.let { initBanner(it) }
    }

    override fun getData(data: ArticleBean) {
    }

    override fun getDataHeader(any: List<BannerInfo>) {
        mBannerList = any
        initBanner(any)
    }

    private fun initBanner(mBannerList: List<BannerInfo>) {
        val imagePath = ArrayList<String>()
        val imageTitle = ArrayList<String>()
        for ((_, _, imagePath1, _, _, title) in mBannerList) {
            imagePath.add(imagePath1)
            imageTitle.add(title)
        }
        bannerGuideContent.setData(imagePath, imageTitle)
        bannerGuideContent.setDelegate { banner, itemView, model, position ->
            nav(bannerGuideContent).navigateAction(
                R.id.action_to_webViewFragmentFragment,
                bundleOf(
                    "title" to mBannerList[position].title,
                    "link" to mBannerList[position].url
                )
            )
        }

        bannerGuideContent.setAdapter { _: BGABanner, view: View, model: Any?, index: Int ->
            ImageLoaderUtils.loadImage(view as ImageView, model as String, context)
        }
    }

}