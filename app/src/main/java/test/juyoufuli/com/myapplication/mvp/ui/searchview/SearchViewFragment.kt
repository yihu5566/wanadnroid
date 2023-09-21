package test.juyoufuli.com.myapplication.mvp.ui.searchview

import android.os.Bundle
import android.view.LayoutInflater
import com.airbnb.mvrx.fragmentViewModel
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ActivitySearchBinding

/**
 * @Author : dongfang
 * @Created Time : 2023-09-13  16:49
 * @Description:
 */
class SearchViewFragment : BaseFragment<ActivitySearchBinding>() {
    val viewModel: SearchViewModel by fragmentViewModel()


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun attachBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(LayoutInflater.from(activity))
    }

    override fun invalidate() {

    }
}