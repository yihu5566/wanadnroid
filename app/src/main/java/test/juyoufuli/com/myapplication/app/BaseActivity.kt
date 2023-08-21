package test.juyoufuli.com.myapplication.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:41
 * @Description:
 */
abstract class BaseActivity<V : ViewBinding>() : AppCompatActivity() {
    // viewbind
    var binding: V? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = attachBinding()
        setContentView(binding?.root)
        initData(savedInstanceState)
        initView(savedInstanceState)
    }

    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 绑定布局Binding
     *
     * @return 布局文件ID
     */
    protected abstract fun attachBinding(): V
}