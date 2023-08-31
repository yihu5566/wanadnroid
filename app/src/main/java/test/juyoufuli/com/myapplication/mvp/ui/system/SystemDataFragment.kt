package test.juyoufuli.com.myapplication.mvp.ui.system

import android.os.Bundle
import android.view.LayoutInflater
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.TabFragmentBinding
import test.juyoufuli.com.myapplication.mvp.views.systemItem

/**
 * Author : ludf
 * Created Time : 2018-09-29  11:20
 * Description:
 */
class SystemDataFragment : BaseFragment<TabFragmentBinding>() {

    val viewModel: SystemViewModel by fragmentViewModel()

    //    private val bindingg: TabFragmentBinding by viewBinding()
    override fun initView(savedInstanceState: Bundle?) {
//        viewModel.onAsync(
//            SystemState::systemBean, uniqueOnly(),
//            onFail = {
//                Snackbar.make(binding.root, "Jokes request failed.", Snackbar.LENGTH_INDEFINITE)
//                    .show()
//            }
//        )
        binding.recyclerView.buildModelsWith(object : EpoxyRecyclerView.ModelBuilderCallback {
            override fun buildModels(controller: EpoxyController) {
                controller.buildModels()
            }
        })
    }

    private fun EpoxyController.buildModels() = withState(viewModel) { state ->
        state.systemBean.invoke()?.data?.forEach { system ->
            var stringName = ""
            system.children.forEach {
                stringName += it.name + ","
            }
            systemItem {
                id(system.id)
                title(system.name)
                des(stringName)
            }

        }
    }

    override fun attachBinding(): TabFragmentBinding {
        return TabFragmentBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
        binding.recyclerView.requestModelBuild()
    }
}
