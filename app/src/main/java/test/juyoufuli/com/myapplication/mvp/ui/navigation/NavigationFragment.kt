package test.juyoufuli.com.myapplication.mvp.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.OnClickListener
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.FragmentNavigationBinding
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectRecycerDecoration
import test.juyoufuli.com.myapplication.mvp.views.navigationItemView


/**
 * Author : dongfang
 * Created Time : 2018-11-12  11:22
 * Description:
 */
class NavigationFragment : BaseFragment<FragmentNavigationBinding>() {

    val viewModel: NavigationViewModel by fragmentViewModel()

    override fun initView(savedInstanceState: Bundle?) {
        binding.epoxyRecyclerView.addItemDecoration(
            ProjectRecycerDecoration(
                requireContext(),
                ProjectRecycerDecoration.VERTICAL_LIST,
                R.drawable.item_left_decoration,
                2
            )
        )
        binding.epoxyRecyclerView.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })

    }

    private fun EpoxyController.buildModels() = withState(viewModel) {
        it.navigationData.invoke()?.data?.forEachIndexed { position, data ->
            if (data.articles.isNotEmpty()) {
                navigationItemView {
                    id(data.cid)
                    title(data.name)
                    val labText = mutableListOf<String>()
                    data.articles.forEach { it1 ->
                        labText.add(it1.title)
                    }
                    labsView(labText)
                    tagVisibility(position == it.selectPosition)
                    clickVisListener(OnClickListener {
                        viewModel.selectPositionItem(position)
                    })
                }
            }
        }
    }

    override fun attachBinding(): FragmentNavigationBinding {
        return FragmentNavigationBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
        binding.epoxyRecyclerView.requestModelBuild()
    }

}