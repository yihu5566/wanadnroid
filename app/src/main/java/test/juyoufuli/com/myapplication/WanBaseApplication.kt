package test.juyoufuli.com.myapplication

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatDelegate

import com.jess.arms.base.BaseApplication

import test.juyoufuli.com.myapplication.app.utils.SPUtils
import android.hardware.Camera.ErrorCallback
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadSir
import test.juyoufuli.com.myapplication.mvp.ui.callback.EmptyCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.LoadingCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.TimeoutCallback


/**
 * Author : dongfang
 * Created Time : 2018-10-1209:19
 * Description:
 */
class WanBaseApplication : BaseApplication() {

    companion object {
        val ui_handler: Handler? = Handler(Looper.getMainLooper())
        var application: BaseApplication? = null

        fun runOnUiThread(runnable: Runnable?) {
            if (runnable != null) {
                ui_handler!!.post(runnable)
            }
        }

        fun getAppContext(): Application? {
            return application
        }
    }


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
        LoadSir.beginBuilder()
                .addCallback(EmptyCallback())
                .addCallback(LoadingCallback())
                .addCallback(TimeoutCallback())
                .setDefaultCallback(LoadingCallback::class.java)

                .commit()

    }


}
