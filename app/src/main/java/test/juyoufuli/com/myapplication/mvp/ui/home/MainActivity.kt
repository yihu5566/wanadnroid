package test.juyoufuli.com.myapplication.app.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.FrameLayout
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import kotlinx.android.synthetic.main.activity_main.*
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.mvp.presenter.HomePresenter
import test.juyoufuli.com.myapplication.mvp.ui.home.MainFragment
import test.juyoufuli.com.myapplication.mvp.ui.tab.SystemDataFragment

class MainActivity : BaseActivity<HomePresenter>(), View.OnClickListener {




    override fun initData(savedInstanceState: Bundle?) {
        flContent = findViewById(R.id.fl_content)
        tv_tab_1.setOnClickListener(this)
        tv_tab_2.setOnClickListener(this)
        tv_tab_1.isSelected = true
        supportFragmentManager.beginTransaction().
                add(R.id.fl_content, MainFragment()).commit()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    private var flContent: FrameLayout? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_tab_1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_content, MainFragment()).commit()
                tv_tab_1.isSelected = true
                tv_tab_2.isSelected = false
            }
            R.id.tv_tab_2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fl_content, SystemDataFragment()).commit()
                tv_tab_1.isSelected = false
                tv_tab_2.isSelected = true
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        LogUtils.d("onSaveInstanceState...")

    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("onStart...")

    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume...")

    }

    override fun onPause() {
        super.onPause()
        LogUtils.d("onPause...")

    }

    override fun onStop() {
        super.onStop()
        LogUtils.d("onStop...")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("onDestroy...")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.d("onConfigurationChanged...")

    }
}
