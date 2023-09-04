package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.airbnb.mvrx.fragmentViewModel
import com.blankj.utilcode.util.ToastUtils
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.FragmentWechatnumberBinding

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  10:24
 * @Description:
 */
class WeChatNumberFragment : BaseFragment<FragmentWechatnumberBinding>() {

    private val viewModel: WeChatPublishViewModel by fragmentViewModel()

    val tagNameList = mutableListOf<String>()

    /**
     * 默认的公众号id
     */
    lateinit var cid: String

    override fun initView(savedInstanceState: Bundle?) {
        binding.fabWechatNumber.setOnClickListener { v -> ToastUtils.showLong("搜索") }
    }

    override fun attachBinding(): FragmentWechatnumberBinding {
        return FragmentWechatnumberBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
        viewModel.onAsync(WeChatPublishState::WeChatPublishBean) {
            tagNameList.clear()
            val children = it.data
            var stringb: StringBuffer
            for (i in children.indices) {
                stringb = StringBuffer()
                stringb.append(children[i].id)
                stringb.append("*")
                stringb.append(children[i].name)
                tagNameList.add(stringb.toString())
            }
            initDataList()
        }
    }

    private fun initDataList() {
        //初始化一下
        cid = tagNameList[0].split("*")[0]
        binding.vpSystemDetailsContent.adapter = ScreenSlidePagerAdapter(parentFragmentManager)
        binding.rivSystemDetailsTop.setupWithViewPager(binding.vpSystemDetailsContent)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = tagNameList.size

        override fun getItem(position: Int): Fragment {
            val fragment = RecyclerViewFragment()
            val cid = tagNameList[position].split("*")[0]
            fragment.arguments = bundleOf(Pair("cid", cid))
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tagNameList[position].split("*")[1]
        }
    }
}