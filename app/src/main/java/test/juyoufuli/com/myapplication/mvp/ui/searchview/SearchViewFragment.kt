package test.juyoufuli.com.myapplication.mvp.ui.searchview

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.blankj.utilcode.util.LogUtils
import com.we.jetpackmvvm.ext.nav
import com.we.jetpackmvvm.ext.navigateAction
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.listener.CustomSearchListener
import test.juyoufuli.com.myapplication.databinding.ActivitySearchBinding
import test.juyoufuli.com.myapplication.mvp.views.articleItemView
import test.juyoufuli.com.myapplication.mvp.views.loadFinishView
import test.juyoufuli.com.myapplication.mvp.views.loadingRow

/**
 * @Author : dongfang
 * @Created Time : 2023-09-13  16:49
 * @Description:
 */
class SearchViewFragment : BaseFragment<ActivitySearchBinding>() {
    private val viewModel: SearchViewModel by fragmentViewModel()
    private var hotWordList = mutableListOf<String>()
    var searchWord: String = ""

    override fun initView(savedInstanceState: Bundle?) {
        binding.includedTitle.toolbarTitle.text = "搜索"
        binding.includedTitle.toolbarBack.visibility = View.VISIBLE
        binding.includedTitle.toolbarBack.setOnClickListener {
            nav().popBackStack()
        }
        binding.lbvSearch.setOnLabelClickListener { _, data, _ ->
            binding.etSearchWord.editTextString = data.toString()
            viewModel.changeSearchWord(data.toString())
        }
        binding.etSearchWord.setCustomSearchListener(object : CustomSearchListener {
            override fun OnContentChangeListener(s: String?) {
                searchWord = if (TextUtils.isEmpty(s)) "" else s!!
                LogUtils.d("搜索--$searchWord")
            }

            override fun OnSearchButtonPressListener(b: Boolean?) {
                viewModel.changeSearchWord(searchWord)
            }
        })
        binding.btnSearchWord.setOnClickListener { viewModel.changeSearchWord(searchWord) }

        binding.rlvSearchResult.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        if (state.pager == 2) {
            binding.lbvSearch.visibility = View.GONE
            if (state.searchArtList.isNotEmpty()) {
                binding.rlvSearchResult.visibility = View.VISIBLE
                binding.tvSearchFinish.visibility = View.GONE
            } else {
                binding.rlvSearchResult.visibility = View.GONE
                binding.tvSearchFinish.visibility = View.VISIBLE
            }
        }

        state.searchArtList.forEach { data ->
            articleItemView {
                id(data.id)
                title(data.chapterName)
                des(Html.fromHtml(data.title))
                time(data.niceDate)
                View.OnClickListener { _ ->
                    nav().navigateAction(
                        R.id.action_to_webViewFragmentFragment,
                        bundleOf("title" to data.title, "link" to data.link)
                    )
                }
            }
        }
        if (state.isLoadFinish) {
            loadFinishView {
                id("load_finish")
            }
            return@withState
        }
        loadingRow {
            id("loading${state.searchArtList.size}")
            onBind { _, _, _ ->
                viewModel.getSearchArtList(
                    state.pager,
                    state.searchWord,
                )
            }
        }
    }


    override fun attachBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(LayoutInflater.from(activity))
    }

    override fun invalidate() {
        viewModel.onAsync(SearchState::searchHotWord) {
            hotWordList.clear()
            for (item in it.data) {
                hotWordList.add(item.name)
            }
            binding.lbvSearch.setLabels(hotWordList)
        }
        binding.rlvSearchResult.requestModelBuild()

    }
}