package test.juyoufuli.com.myapplication.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.MavericksView

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:41
 * @Description:
 */
abstract class BaseFragment<V : ViewBinding> : Fragment(), MavericksView {
    // viewbind
    lateinit var binding: V
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = attachBinding()
        initView(savedInstanceState)
        return binding.root
    }


    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 绑定布局Binding
     *
     * @return 布局文件ID
     */
    protected abstract fun attachBinding(): V
}