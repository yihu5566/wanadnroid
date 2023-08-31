package test.juyoufuli.com.myapplication.mvp.ui.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.OnClickListener
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ActivityProgectMainBinding
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectRecycerDecoration
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import test.juyoufuli.com.myapplication.mvp.views.loadingRow
import test.juyoufuli.com.myapplication.mvp.views.projectCategory
import test.juyoufuli.com.myapplication.mvp.views.projectCategoryDetails


/**
 * Author : dongfang
 * Created Time : 2018-10-31  09:27
 * Description:
 */
class ProjectFragment : BaseFragment<ActivityProgectMainBinding>() {
    val viewModel: ProjectViewModel by fragmentViewModel()

    override fun initView(savedInstanceState: Bundle?) {
        binding.rlvProject.addItemDecoration(
            ProjectRecycerDecoration(
                requireContext(),
                ProjectRecycerDecoration.VERTICAL_LIST,
                R.drawable.item_left_decoration,
                2
            )
        )
        binding.rlvProjectContent.addItemDecoration(
            ProjectRecycerDecoration(
                requireContext(),
                ProjectRecycerDecoration.VERTICAL_LIST,
                R.drawable.item_decoration,
                2
            )
        )
        binding.rlvProject.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })

        binding.rlvProjectContent.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModelsContext()
            }
        })

        //cid 变化需要刷新列表
        viewModel.onEach(ProjectState::cid) {
            viewModel.requestProjectCategoryDetailsList(1, it)
        }
    }

    override fun attachBinding(): ActivityProgectMainBinding {
        return ActivityProgectMainBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
        binding.rlvProject.requestModelBuild()
        binding.rlvProjectContent.requestModelBuild()
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        if (state.projectTitle.complete) {
            state.projectTitle.invoke()?.data?.forEachIndexed { position, it ->
                projectCategory {
                    id(it.id)
                    title(it.name)
                    tagVisibility(state.selectIndex == position)
                    clickListener { it1 ->
                        ToastUtils.showLong("点击条目了${it.id}")
                        viewModel.changeTab(position, it)
                    }
                }
            }
        }

    }

    private fun EpoxyController.buildModelsContext() = withState(viewModel) { state ->
        state.projectDetailsList.forEach {
            projectCategoryDetails {
                id(it.id)
                title(it.title)
                des(it.desc)
                time(it.niceDate)
                author(it.author)
                isCollect(
                    when (it.collect) {
                        true -> "1"
                        false -> "0"
                    }
                )
                imageUrl(it.envelopePic)
                OnClickListener { it1 ->
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("link", it.link)
                    intent.putExtra("title", it.title)
                    ActivityUtils.startActivity(intent)
                }
            }
        }
        loadingRow {
            // Changing the ID will force it to rebind when new data is loaded even if it is
            // still on screen which will ensure that we trigger loading again.
            id("loading${state.projectDetailsList.size}")
            onBind { _, _, _ ->
                viewModel.requestProjectCategoryDetailsList(
                    state.pager,
                    state.cid,
                )
            }
        }
    }
}