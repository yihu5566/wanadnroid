package test.juyoufuli.com.myapplication

import android.support.v7.app.AppCompatDelegate

import com.jess.arms.base.BaseApplication

import test.juyoufuli.com.myapplication.app.utils.SPUtils

/**
 * Author : dongfang
 * Created Time : 2018-10-1209:19
 * Description:
 */
class WanBaseApplication : BaseApplication() {
    override fun onCreate() {
        init()
        super.onCreate()
    }

    private fun init() {
        //判断是不是夜间模式
        val mode = SPUtils.get(this, "night_mode", false) as Boolean
        //进入方法就先注册上监听
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
