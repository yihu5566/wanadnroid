package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.tabs.TabLayoutMediator
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
    var cid: String = "0"

    override fun initView(savedInstanceState: Bundle?) {
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
        binding.vpSystemDetailsContent.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.vpSystemDetailsContent.adapter =
            object : FragmentStateAdapter(childFragmentManager, lifecycle) {
                override fun getItemCount(): Int {
                    return tagNameList.size
                }

                override fun createFragment(position: Int): Fragment {
                    var cid = tagNameList[position].split("*")[0]
                    return RecyclerViewFragment(cid)
                }

            }

        val mediator = TabLayoutMediator(
            binding.rivSystemDetailsTop, binding.vpSystemDetailsContent
        ) { tab, position ->
            run {
                var index = if (position >= tagNameList.size) {
                    tagNameList.size - 1
                } else {
                    position
                }
                tab.text = tagNameList[index].split("*")[1]
            }
        }
        mediator.attach()
        binding.vpSystemDetailsContent.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })
    }

}