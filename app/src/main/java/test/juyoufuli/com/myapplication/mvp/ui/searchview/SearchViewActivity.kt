package test.juyoufuli.com.myapplication.mvp.ui.searchview

import android.os.Bundle
import android.view.LayoutInflater
import test.juyoufuli.com.myapplication.app.BaseActivity
import test.juyoufuli.com.myapplication.databinding.ActivitySearchBinding

/**
 * @Author : dongfang
 * @Created Time : 2023-09-13  16:49
 * @Description:
 */
class SearchViewActivity : BaseActivity<ActivitySearchBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun attachBinding(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(LayoutInflater.from(this))

    }
}