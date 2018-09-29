package test.juyoufuli.com.myapplication.app.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.ui.tab.SystemDataFragment
import test.juyoufuli.com.myapplication.app.utils.LogUtils

class MainActivity : FragmentActivity(), View.OnClickListener {
    private var flContent: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtils.d("onCreate...")
        initData()
    }

    private fun initData() {
        tv_tab_1.isSelected=true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_tab_1 -> {
                supportFragmentManager.beginTransaction().add(R.id.fl_content, MainFragment()).commit()
                tv_tab_1.isSelected=true
                tv_tab_2.isSelected=false
            }
            R.id.tv_tab_2 -> {
                supportFragmentManager.beginTransaction().add(R.id.fl_content, SystemDataFragment()).commit()
                tv_tab_1.isSelected=false
                tv_tab_2.isSelected=true
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
        flContent = findViewById(R.id.fl_content)
        tv_tab_1.setOnClickListener(this)
        tv_tab_2.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume...")
        supportFragmentManager.beginTransaction().add(R.id.fl_content, MainFragment()).commit()
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
