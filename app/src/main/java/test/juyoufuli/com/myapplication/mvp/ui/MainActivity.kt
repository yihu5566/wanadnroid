package test.juyoufuli.com.myapplication.mvp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseActivity
import test.juyoufuli.com.myapplication.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {
    var exitTime = 0L

    @SuppressLint("ResourceAsColor")
    override fun initView(savedInstanceState: Bundle?) {
        //返回拦截
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainfragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }


    override fun attachBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(LayoutInflater.from(this))
    }
}
