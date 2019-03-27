package test.juyoufuli.com.myapplication

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatDelegate

import com.jess.arms.base.BaseApplication

import test.juyoufuli.com.myapplication.app.utils.SPUtils

/**
 * Author : dongfang
 * Created Time : 2018-10-1209:19
 * Description:
 */
class WanBaseApplication : BaseApplication() {

    companion object {
        val ui_handler: Handler? = Handler(Looper.getMainLooper())

        fun runOnUiThread(runnable: Runnable?) {
            if (runnable != null) {
                ui_handler!!.post(runnable)
            }
        }
    }

    private var application: BaseApplication? = null

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        application = this

        //判断是不是夜间模式
        val mode = SPUtils.get(this, "night_mode", false) as Boolean
        //进入方法就先注册上监听
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


    }

    fun getAppContext(): Application? {
        return application
    }

}
