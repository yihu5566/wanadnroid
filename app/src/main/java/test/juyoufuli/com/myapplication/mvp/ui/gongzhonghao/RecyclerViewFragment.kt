package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ViewPagerItemBinding
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectRecycerDecoration
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import test.juyoufuli.com.myapplication.mvp.views.articleItemView
import test.juyoufuli.com.myapplication.mvp.views.loadFinishView
import test.juyoufuli.com.myapplication.mvp.views.loadingRow

/**
 * Author : dongfang
 * Created Time : 2018-10-17  16:33
 * Description: wiewpager 用的fragment
 */
class RecyclerViewFragment(private var cid: String) : BaseFragment<ViewPagerItemBinding>() {

    private val viewModel: WeChatPublishDetailsViewModel by fragmentViewModel()

    override fun initView(savedInstanceState: Bundle?) {
        LogUtils.d("---initData---$cid")
        binding.epoxyRecyclerView.addItemDecoration(
            ProjectRecycerDecoration(
                requireContext(),
                ProjectRecycerDecoration.VERTICAL_LIST,
                R.drawable.item_left_decoration,
                2
            )
        )
        binding.epoxyRecyclerView.buildModelsWith(object :
            EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })
    }

    override fun attachBinding(): ViewPagerItemBinding {
        return ViewPagerItemBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
        binding.epoxyRecyclerView.requestModelBuild()
    }

    private fun EpoxyController.buildModels() = withState(viewModel) {

        it.articleBeanList.forEach { data ->
            articleItemView {
                id(data.id)
                title(data.chapterName)
                des(Html.fromHtml(data.title))
                time(data.niceDate)
                clickListener { v ->
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("link", data.link)
                    intent.putExtra("title", data.title)
                    ActivityUtils.startActivity(intent)
                }
            }
        }

        if (it.isLoadFinish) {
            loadFinishView {
                id("load_finish")
            }
            return@withState
        }

        loadingRow {
            id("loading_article")
            onBind { _, _, _ ->
                viewModel.getArticleListDetailsList(
                    cid.toInt(),
                    it.page,
                )
            }
        }

    }

    fun setData(cid: String) {
        this.cid = cid
        binding.epoxyRecyclerView.requestModelBuild()
    }

}
