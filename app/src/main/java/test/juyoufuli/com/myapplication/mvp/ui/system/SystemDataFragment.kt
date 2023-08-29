package test.juyoufuli.com.myapplication.mvp.ui.system

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.databinding.TabFragmentBinding
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean
import test.juyoufuli.com.myapplication.mvp.ui.system.adapter.SystemDataAdapter
import test.juyoufuli.com.myapplication.mvp.viewmodel.SystemViewModel

/**
 * Author : ludf
 * Created Time : 2018-09-29  11:20
 * Description:
 */
class SystemDataFragment : BaseFragment<TabFragmentBinding>() {

    val viewModel: SystemViewModel by fragmentViewModel()
    var systemList = mutableListOf<SystemBean>()
    lateinit var systemDataAdapter: SystemDataAdapter

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.getSystemDataList()
        systemDataAdapter = SystemDataAdapter(requireContext(), systemList)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = systemDataAdapter

    }

    override fun attachBinding(): TabFragmentBinding {
        return TabFragmentBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
        withState(viewModel) {
            LogUtils.d("获取知识体系数据${it.systemBean.invoke()?.data?.size}")
            systemList.clear()
            systemList.addAll(it.systemBean.invoke()?.data ?: emptyList())
            systemDataAdapter.notifyDataSetChanged()
//            for (child in it.systemBean.invoke()?.data?.children ?: emptyList()) {
//
//            }

        }
    }
}
