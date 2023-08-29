package test.juyoufuli.com.myapplication.mvp.ui.system

import android.os.Bundle
import android.view.LayoutInflater
import com.blankj.utilcode.util.LogUtils
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ViewPagerItemBinding
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean

/**
 * Author : dongfang
 * Created Time : 2018-10-17  16:33
 * Description: wiewpager 用的fragment
 */
class RecyclerViewFragment : BaseFragment<ViewPagerItemBinding>() {
    internal var mSystemData: List<ArticleBean> = listOf()
    private var cid: String = ""
    private var isLoadingMore: Boolean = false
    private var page: Int = 0
    private var totalPage: Int = 0

    private var isFrist = true

    override fun initView(savedInstanceState: Bundle?) {
        LogUtils.d("---initData---")
        if (isFrist) {
            isFrist = false
            cid = arguments?.getString("cid").toString()
        }
    }

    override fun attachBinding(): ViewPagerItemBinding {
        return ViewPagerItemBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {

    }
}
